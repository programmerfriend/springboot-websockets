function connect() {
  ws = new WebSocket('ws://localhost:8080/tweets');
  ws.onmessage = function (data) {
    console.log(data);
    showGreeting(data.data);
  }
}

function disconnect() {
  if (ws != null) {
    ws.close();
  }
  console.log("Disconnected");
}

function showGreeting(message) {
  var jsonMsg = JSON.parse(message);
  console.log(jsonMsg);
  document.querySelector('#messages').innerHTML += "<tr><th>" + jsonMsg.status.id + "</th><th>"+jsonMsg.status.text+"</th></tr>";
}
