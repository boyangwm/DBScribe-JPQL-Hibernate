$(document).ready(function(){
    // auto-refresh task
    setInterval('updateChatHistory()', 30000); // that's 30 seconds

    // Post Message click handler
    $('input#postMessage').click(onClickPostMessage);

    // MessagesPerPage radio button change handler
    $('input:radio[name="messagesPerPage"]').change(setMessagesPerPage);
});


function onClickPostMessage() {
    var text = $('textarea#chatMessage').val();
    if ($.trim(text)) {
        postChatMessage(text);
    } else {
        $('textarea#chatMessage').val('');
        $('span#flash').attr("class", "error");
        $('span#flash').html('The message is empty');
        $('span#flash').fadeIn(2000)
        $('span#flash').delay(5000).fadeOut(2000);
    }
}

function updateChatHistory(){
    //get new content through ajax
    $.ajax({
        type : 'GET',
        url : '/loadChatHistoryAJAX.json',
        dataType : 'json',
        success : function(data){
            $("#chat").fadeOut(1000);
            $('#chat').html(data.data);
            $('#chat').fadeIn(1000);
        },
        error : function(e) {
        }
    });
}

function postChatMessage(message) {
    $.ajax({
        type : 'POST',
        url : '/postMessageAJAX.json',
        dataType : 'json',
        data : {
            text : message
        },
        success : function(response){
            if (response.status == 'SUCCESS') {
                $('textarea#chatMessage').val('');
                $('span#flash').attr("class", "success");
                $('span#flash').html('The message was posted successfully');
                $('span#flash').fadeIn(2000)
                $('span#flash').delay(5000).fadeOut(2000);
            }
        },
        error : function(e) {
        }
    }).fail(function(jqXHR) {
        $('span#flash').attr("class", "error");
        $('span#flash').html('Failed to post the message: ' + jqXHR.statusText);
        $('span#flash').fadeIn(2000)
        $('span#flash').delay(5000).fadeOut(2000);
    });
}

function setMessagesPerPage(){
    $.ajax({
        type : 'POST',
        url : '/setMessagesPerPagePropAJAX.json',
        dataType : 'json',
        data : {
            limit : $(this).val()
        },
        success : function(data){
        },
        error : function(e) {
        }
    });
    // and now update chat history
    updateChatHistory();
}