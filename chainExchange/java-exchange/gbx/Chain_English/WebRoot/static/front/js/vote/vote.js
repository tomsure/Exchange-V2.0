var vote = {
    supportSubmit: function(obj, vote) {
        var fid = $("#fid").val();
        var islogin =$("#islogin").val();
        if ("false" == islogin) return void(window.location.href = "/user/login.html?forwardUrl=/vote/details.html?id=" + fid);
        var url = "/json/coinVote.html";
        var param = {
        	vote: vote,
            id: fid
        };
    	$.post(url, param, function(result) {
    		if (result != null) {
    			if (result.code < 0) {
    				util.layerAlert("", result.msg, 2);
    			} else if (result.code == 0) {
    				util.showerrortips("", result.msg, {
    					okbtn : function() {
    						$('#alertTips').modal('hide');
    						location.reload(true);
    					}
    				});
    			}
    		}
    	});
    }
};
$(function() {
    $("#support").on("click",
    function() {
        vote.supportSubmit(this, 1);
    });
    $("#nosupport").on("click",
    function() {
        vote.supportSubmit(this, 0);
    })
});