<content:title>
    <fmt:message key="edit.number" />
</content:title>

<content:section cssId="numberEditPage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="number">
            <tag:formErrors modelAttribute="number" />

            <div class="row">
                <form:hidden path="locale" value="${number.locale}" />
                <form:hidden path="revisionNumber" value="${number.revisionNumber}" />
                
                <c:if test="${number.locale.language == 'ar'}">
                    <div id="symbolContainer" class="input-field col s12" <c:if test="${number.locale.language != 'ar'}"> style="display: none;" </c:if> >
                        <form:label path="symbol" cssErrorClass="error"><fmt:message key='symbol' /></form:label>
                        <form:input path="symbol" cssErrorClass="error" />
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <form:label path="value" cssErrorClass="error"><fmt:message key='value' /> (<fmt:message key='number' />)</form:label>
                    <form:input path="value" cssErrorClass="error" type="number" />
                </div>
                
                <%-- TODO: word --%>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="edit" /> <i class="material-icons right">send</i>
            </button>
            <a href="<spring:url value='/content/number/delete/${number.id}' />" class="waves-effect waves-red red-text btn-flat right"><fmt:message key="delete" /></a>
        </form:form>
    </div>
    
    <div class="divider"></div>
    
    <%--<p>
        <fmt:message key="last.update" />:<br />
        <div class="chip">
            <img src="${number.contributor.imageUrl}" alt="" class="circle responsive-img">
            <c:out value="${number.contributor.firstName}" />&nbsp;<c:out value="${number.contributor.lastName}" />
        </div> 
        <fmt:formatDate value="${number.calendar.time}" type="both" timeStyle="short" />
    </p>--%>
    
    <div id="disqus_thread"></div>
    <script>
    /**
    * RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
    * LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
    */
    /*
    var disqus_config = function () {
    this.page.url = PAGE_URL; // Replace PAGE_URL with your page's canonical URL variable
    this.page.identifier = PAGE_IDENTIFIER; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    };
    */
    (function() { // DON'T EDIT BELOW THIS LINE
    var d = document, s = d.createElement('script');

    s.src = '//literacyapp.disqus.com/embed.js';

    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
    })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
</content:section>

<content:aside>
    <h5 class="center"><fmt:message key="preview" /></h5>
    
    <div class="previewContainer valignwrapper">
        <img src="<spring:url value='/img/device-pixel-c.png' />" alt="<fmt:message key="preview" />" />
        <div id="previewContentContainer">
            <div id="previewContent" class="previewContentGrapheme">

            </div>
        </div>
    </div>
    <script>
        $(function() {
            initializePreview();
            
            $('#symbol, #value').on("change", function() {
                console.debug('#symbol/#value on change');
                initializePreview();
            });
            
            function initializePreview() {
                console.debug('initializePreview');
                var symbol = $('#symbol').val();
                var value = $('#value').val();
                if ((symbol != undefined) && (symbol != "")) {
                    $('#previewContent').html(symbol);
                } else {
                    $('#previewContent').html(value);
                }
            };
        });
    </script>
</content:aside>
