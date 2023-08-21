MazeManagerState = {
	UNINITIALIZED: 0,
	LOADING: 1,
	LOADED: 2,
	NETWORK_ERROR: 3,
	SERVER_ERROR: 4
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
		
		xhr.open("GET", "/Maze?data="+encodeURIComponent(JSON.stringify(requestData)), true);
		xhr.send(null);
	}		
	
	this.onRequestResult = function(response) {
		try {			
			this.mazeData = JSON.parse(response);
			
			if (this.mazeData.status == 0)
				this.setState(MazeManagerState.LOADED);
			else 
				this.setState(MazeManagerState.SERVER_ERROR);
		} catch (exception) {
			this.setState(MazeManagerState.INCORRECT_RESPONSE_ERROR);
		}
	}	
}