$(function(){
	$(".navbar").load("/bluejay-server/navbar.html");
	$("post").click(function(event){
		var post = {};
		post.postid = $(this).attr("postid");
		post.userid = $(this).attr("userid");
		post.content = $(this).html();
		var target = $(this);
		
		//POST works, GET doesn't
		
		/*$.ajax({
			url:'/bluejay-server/rest/testEcho',
			type:'POST',
			dataType:'json',
			contentType: 'application/json',
			processData:false,
			data: JSON.stringify(post),
			success:function(data){
				target.html(data.content + " " +data.content);
			},
			error:function(data){
				target.html("Failure");
			}
		});*/
		$.ajax({
			url:'/bluejay-server/rest',
			type:'GET',
			dataType:'json',
			contentType: 'application/json',
			processData:false,
			data: JSON.stringify(post),
			success:function(data){
				target.html(data.content + " " +data.content);
			},
			error:function(data){
				target.html("Failure");
			}
		});
		
	});
});