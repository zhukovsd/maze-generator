var FormMazeRequestData = new function() {
	this.geometry = 0;

	this.getRequestData = function() {
		var requestData = {};
		requestData.geometry = this.geometry;
		requestData.views = [MazeGraphKind.RESULTING_GRAPH, MazeGraphKind.SHORTEST_PATH];		
		requestData.availableWidth = document.getElementById("mazecanvascontainer").clientWidth;
		
		requestData.size = {};
		if (this.geometry == 0) {
			requestData.size.rowCount = document.getElementById("rectrowcountedit").value;
			requestData.size.columnCount = document.getElementById("rectcolumncountedit").value;
		} else if (this.geometry == 1) {
			requestData.size.circleCount = document.getElementById("circlecountedit").value;
		} else if (this.geometry == 2) {
			requestData.size.rowCount = document.getElementById("hexrowcountedit").value;
			requestData.size.columnCount = document.getElementById("hexcolumncountedit").value;			
		}
		
		return requestData;
	}
}