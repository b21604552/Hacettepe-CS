<html>
<head>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
	<script> 
	var websocket = null;
	function showMessage(data) {
		if(data.message_type == 'userInfo'){
			var tbodyRef = document.getElementById('table1');
			var newRow = tbodyRef.insertRow();
			for (const property in data) {
				if(data[property] != null){
					if(property != 'cookie' && property != 'message_type'){
						var newCell = newRow.insertCell();
						var newText = document.createTextNode(data[property]);
						newCell.appendChild(newText);
					}else if(property == 'cookie'){
						var cookies = data[property].split(";");
						var newCell = newRow.insertCell();
						var newText = document.createTextNode(cookies[5]);
						newCell.appendChild(newText);
						newCell = newRow.insertCell();
						newText = document.createTextNode(data[property]);
						newCell.appendChild(newText);
						newCell = newRow.insertCell();
						var d = new Date();
						newText = document.createTextNode(d);
						newCell.appendChild(newText);
					}
				}
			}
		}else{
			alert(data.message);
		}
	}

	$(document).ready(function(){
		websocket = new WebSocket("ws://localhost:8090/demo/php-socket.php"); 
		websocket.onopen = function(event) { 
			alert("Connection is established!");		
		}
		websocket.onmessage = function(event) {
			var Data = JSON.parse(event.data);
			showMessage(Data);
		};
		
		websocket.onerror = function(event){
			alert("Problem due to some Error.");
		};
		websocket.onclose = function(event){
			alert("Connection Closed.");
		}; 
	});
	</script>
	</head>
	<body>
		<table id="table1" class="table table-dark">
			  <tr>
				<th scope="col">Client Ip Address</th>
				<th scope="col">Client Port</th>
				<th scope="col">Browser Information</th>
				<th scope="col">Client Operating System</th>
				<th scope="col">Referrer</th>
				<th scope="col">Session ID</th>
				<th scope="col">Cookie</th>
				<th scope="col">Date</th>
			  </tr>
		</table>
</body>
</html>