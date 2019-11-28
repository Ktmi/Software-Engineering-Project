var bj = {};

bj.getUpdatePost = function (target)
{
	var post = {}
	post.postid = target.attr("postid");
	post.userid = target.attr("userid");
	$.ajax({
		url:'/bluejay-server/rest/post',
		type:'POST',
		dataType:'json',
		contentType: 'application/json',
		processData:false,
		data: JSON.stringify(post),
		success:function(data){
			target.html(data.content);
		}
	});
};


$(function(){
	$(".navbar").load("/bluejay-server/navbar.html");
	$("post").click(function(event){
		target = $(this);
		bj.getUpdatePost(target);
	});
});