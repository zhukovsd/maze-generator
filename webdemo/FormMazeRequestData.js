var FormMazeRequestData = new function() {
	this.getRequestData = function() {
		geometryIndex = document.getElementById("mazegeometryselect").selectedIndex;		
		
		var requestData = {};
		requestData.geometry = geometryIndex;
		requestData.views = [MazeGraphKind.RESULTING_GRAPH, MazeGraphKind.SHORTEST_PATH];				
		
		requestData.size = {};
		if (geometryIndex == 0) {
			requestData.size.rowCount = document.getElementById("rowcountedit").value;
			requestData.size.columnCount = document.getElementById("columncountedit").value;
		} else if (geometryIndex == 1) {
			requestData.size.circleCount = document.getElementById("circlecountedit").value;
		}
		
		return requestData;
	}
}