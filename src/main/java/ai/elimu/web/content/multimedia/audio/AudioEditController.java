package ai.elimu.web.content.multimedia.audio;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.content.AudioFormat;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/audio/edit")
public class AudioEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Audio audio = audioDao.read(id);
        model.addAttribute("audio", audio);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());
        
        model.addAttribute("letters", letterDao.readAllOrdered());
        model.addAttribute("numbers", numberDao.readAllOrdered());
        model.addAttribute("words", wordDao.readAllOrdered());
        model.addAttribute("emojisByWordId", getEmojisByWordId());

        return "content/multimedia/audio/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            Audio audio,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isBlank(audio.getTranscription())) {
            result.rejectValue("transcription", "NotNull");
        } else {
            Audio existingAudio = audioDao.read(audio.getTranscription());
            if ((existingAudio != null) && !existingAudio.getId().equals(audio.getId())) {
                result.rejectValue("transcription", "NonUnique");
            }
        }
        
        try {
            byte[] bytes = multipartFile.getBytes();
            if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
                result.rejectValue("bytes", "NotNull");
            } else {
                String originalFileName = multipartFile.getOriginalFilename();
                logger.info("originalFileName: " + originalFileName);
                if (originalFileName.toLowerCase().endsWith(".mp3")) {
                    audio.setAudioFormat(AudioFormat.MP3);
                } else if (originalFileName.toLowerCase().endsWith(".ogg")) {
                    audio.setAudioFormat(AudioFormat.OGG);
                } else if (originalFileName.toLowerCase().endsWith(".wav")) {
                    audio.setAudioFormat(AudioFormat.WAV);
                } else {
                    result.rejectValue("bytes", "typeMismatch");
                }

                if (audio.getAudioFormat() != null) {
                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    audio.setContentType(contentType);

                    audio.setBytes(bytes);

                    // TODO: convert to a default audio format?
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("audio", audio);
            model.addAttribute("contentLicenses", ContentLicense.values());
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            model.addAttribute("letters", letterDao.readAllOrdered());
            model.addAttribute("numbers", numberDao.readAllOrdered());
            model.addAttribute("words", wordDao.readAllOrdered());
            model.addAttribute("emojisByWordId", getEmojisByWordId());
            return "content/multimedia/audio/edit";
        } else {
            audio.setTranscription(audio.getTranscription().toLowerCase());
            audio.setTimeLastUpdate(Calendar.getInstance());
            audio.setRevisionNumber(audio.getRevisionNumber() + 1);
            audioDao.update(audio);
            
            return "redirect:/content/multimedia/audio/list#" + audio.getId();
        }
    }
    
    /**
     * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
     * <p></p>
     * Fixes this error message:
     * "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property 'bytes[0]'"
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    	logger.info("initBinder");
    	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
    
    @RequestMapping(value = "/{id}/add-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleAddContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
    	logger.info("handleAddContentLabelRequest");
        
        logger.info("id: " + id);
        Audio audio = audioDao.read(id);
        
        String letterIdParameter = request.getParameter("letterId");
        logger.info("letterIdParameter: " + letterIdParameter);
        if (StringUtils.isNotBlank(letterIdParameter)) {
            Long letterId = Long.valueOf(letterIdParameter);
            Letter letter = letterDao.read(letterId);
            Set<Letter> letters = audio.getLetters();
            if (!letters.contains(letter)) {
                letters.add(letter);
                audio.setRevisionNumber(audio.getRevisionNumber() + 1);
                audioDao.update(audio);
            }
        }
        
        String numberIdParameter = request.getParameter("numberId");
        logger.info("numberIdParameter: " + numberIdParameter);
        if (StringUtils.isNotBlank(numberIdParameter)) {
            Long numberId = Long.valueOf(numberIdParameter);
            Number number = numberDao.read(numberId);
            Set<Number> numbers = audio.getNumbers();
            if (!numbers.contains(number)) {
                numbers.add(number);
                audio.setRevisionNumber(audio.getRevisionNumber() + 1);
                audioDao.update(audio);
            }
        }
        
        String wordIdParameter = request.getParameter("wordId");
        logger.info("wordIdParameter: " + wordIdParameter);
        if (StringUtils.isNotBlank(wordIdParameter)) {
            Long wordId = Long.valueOf(wordIdParameter);
            Word word = wordDao.read(wordId);
            Set<Word> words = audio.getWords();
            if (!words.contains(word)) {
                words.add(word);
                audio.setRevisionNumber(audio.getRevisionNumber() + 1);
                audioDao.update(audio);
            }
        }
        
        return "success";
    }
    
    @RequestMapping(value = "/{id}/remove-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleRemoveContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
    	logger.info("handleRemoveContentLabelRequest");
        
        logger.info("id: " + id);
        Audio audio = audioDao.read(id);
        
        String letterIdParameter = request.getParameter("letterId");
        logger.info("letterIdParameter: " + letterIdParameter);
        if (StringUtils.isNotBlank(letterIdParameter)) {
            Long letterId = Long.valueOf(letterIdParameter);
            Letter letter = letterDao.read(letterId);
            Set<Letter> letters = audio.getLetters();
            Iterator<Letter> iterator = letters.iterator();
            while (iterator.hasNext()) {
                Letter existingLetter = iterator.next();
                if (existingLetter.getId().equals(letter.getId())) {
                    iterator.remove();
                }
            }
            audio.setRevisionNumber(audio.getRevisionNumber() + 1);
            audioDao.update(audio);
        }
        
        String numberIdParameter = request.getParameter("numberId");
        logger.info("numberIdParameter: " + numberIdParameter);
        if (StringUtils.isNotBlank(numberIdParameter)) {
            Long numberId = Long.valueOf(numberIdParameter);
            Number number = numberDao.read(numberId);
            Set<Number> numbers = audio.getNumbers();
            Iterator<Number> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                Number existingNumber = iterator.next();
                if (existingNumber.getId().equals(number.getId())) {
                    iterator.remove();
                }
            }
            audio.setRevisionNumber(audio.getRevisionNumber() + 1);
            audioDao.update(audio);
        }
        
        String wordIdParameter = request.getParameter("wordId");
        logger.info("wordIdParameter: " + wordIdParameter);
        if (StringUtils.isNotBlank(wordIdParameter)) {
            Long wordId = Long.valueOf(wordIdParameter);
            Word word = wordDao.read(wordId);
            Set<Word> words = audio.getWords();
            Iterator<Word> iterator = words.iterator();
            while (iterator.hasNext()) {
                Word existingWord = iterator.next();
                if (existingWord.getId().equals(word.getId())) {
                    iterator.remove();
                }
            }
            audio.setRevisionNumber(audio.getRevisionNumber() + 1);
            audioDao.update(audio);
        }
        
        return "success";
    }
    
    private Map<Long, String> getEmojisByWordId() {
        logger.info("getEmojisByWordId");
        
        Map<Long, String> emojisByWordId = new HashMap<>();
        
        for (Word word : wordDao.readAll()) {
            String emojiGlyphs = "";
            
            List<Emoji> emojis = emojiDao.readAllLabeled(word);
            for (Emoji emoji : emojis) {
                emojiGlyphs += emoji.getGlyph();
            }
            
            if (StringUtils.isNotBlank(emojiGlyphs)) {
                emojisByWordId.put(word.getId(), emojiGlyphs);
            }
        }
        
        return emojisByWordId;
    }
}
