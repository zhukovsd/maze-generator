MazeGraphKind = {
	INITIAL_GRAPH: 0, DUAL_GRAPH: 1, PATH_SPANNING_TREE: 2, SHORTEST_PATH: 3, RESULTING_GRAPH: 4
}	

var MazeView = new function() {
	var canvasContainer;
	var canvas = null;
	var canvasContext = null;
	
	var width = 5;
	var height = 0;
	
	var graphsData = new Array();	
	
	this.init = function() {
		canvasContainer = document.getElementById("mazecanvascontainer");
		canvas = document.getElementById("mazecanvas");
		canvasContext = canvas.getContext("2d");	
	}
	
	this.setMazeData = function(mazeData) {
		for (var i = 0; i < mazeData.graphs.length; i++) {
			var kind = mazeData.graphs[i].graphKind;
		
			graphsData[kind] = {};
			graphsData[kind].imgBase64Data = mazeData.graphs[i].graphImageString;
			graphsData[kind].isVisible = true;
		}
	}
	
	function setSize(awidth, aheight) {
		if ((width != awidth) || (height != aheight)) {
			width = awidth;
			height = aheight;
			
			canvasContainer.style.height = height + "px";
			
			canvas.style.width = width+"px";
			canvas.style.height = (height)+"px";
			
			canvas.width = canvas.clientWidth;
			canvas.height = canvas.clientHeight;

			// if scrollbar is visible, then canvasContainer height = canvas height + scrollbar height
			if (canvasContainer.scrollHeight > canvasContainer.clientHeight)
				canvasContainer.style.height = (height + (canvasContainer.scrollHeight - canvasContainer.clientHeight)) + "px";
			else
				canvasContainer.style.height = height + "px";		
		}
	}
	
	this.paint = function() {
		canvasContext.clearRect(0, 0, canvas.width, canvas.height);
		
		for (var kind in graphsData) {
			var graph = graphsData[kind];
			
			if (graph.isVisible == true ) {
				if (typeof graph.image == "undefined") {
					graph.image = new Image();
					
					graph.image.onload = function() {
						setSize(graph.image.width, graph.image.height);
						// if (canvas.width != graph.image.width) canvas.width = graph.image.width;
						// if (canvas.height != graph.image.height) canvas.height = graph.image.height;
  			
						canvasContext.drawImage(this, 0, 0);
					};

					graph.image.src = "data:image/png;base64," + graph.imgBase64Data;					
				}
				else {
					canvasContext.drawImage(graph.image, 0, 0);
				}
			}
		}
	}
	
	this.getVisibleByKind = function(kind) {
		return graphsData[kind].isVisible;
	}
	
	this.setVisibleByKind = function(kind, isVisible) {
		if (graphsData[kind].isVisible != isVisible) {
			graphsData[kind].isVisible = isVisible;
			this.paint();
		}
	}
}