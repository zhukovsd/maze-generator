MazeManagerState = {
	UNINITIALIZED: 0,
	LOADING: 1,
	LOADED: 2,
	NETWORK_ERROR: 3,
	INCORRECT_RESPONSE_ERROR: 4
}	

var MazeManager = new function() {
	this.state = MazeManagerState.UNINITIALIZED;
	this.mazeData = {};
	
	this.setState = function(state) {
		this.state = state;
		
		if (typeof this.onStateChange !== 'undefined') {
			this.onStateChange(this.state)
		}		
	}
	
	this.requestMaze = function(requestData) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange=function() {
			switch (xhr.readyState) {
				case 0: // UNINITIALIZED
				case 1: // LOADING
					MazeManager.setState(MazeManagerState.LOADING);
					break;
				case 2: // LOADED
				case 3: // INTERACTIVE
					break;
				case 4: // COMPLETED
					if (xhr.status == 200) {
						MazeManager.onRequestResult(xhr.responseText);
						break;
					}
				default: MazeManager.setState(MazeManagerState.NETWORK_ERROR);
			}			
		};
		
		// xhr.open("GET", "http://localhost:8085/Maze?data="+encodeURIComponent(JSON.stringify(requestData)), true);
		xhr.open("GET", "http://93.100.179.76:8085/Maze?data="+encodeURIComponent(JSON.stringify(requestData)), true);
		// xhr.open("GET", "http://192.168.1.101:8085/Maze?data="+encodeURIComponent(JSON.stringify(requestData)), true);
		xhr.send(null);
	}		
	
	this.onRequestResult = function(response) {
		try {			
			this.mazeData = JSON.parse(response);
			this.setState(MazeManagerState.LOADED);
		} catch (exception) {
			this.setState(MazeManagerState.INCORRECT_RESPONSE_ERROR);
		}
	}	
}