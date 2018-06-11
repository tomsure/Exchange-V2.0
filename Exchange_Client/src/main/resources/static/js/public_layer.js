function layerTips(content, tipElement, layerWidth, layerHeight) {
	layui.use('layer', function() {
		var layer = layui.layer
		layer.config({

		})
		layer.tips(content, tipElement, {
			tips: 4,

		})
	})

}

function layerOpen(content) {
	layui.use('layer', function() {
		var layer = layui.layer
		layer.config({

		})
		layer.msg('1111', {
			icon: 1,
			time: 2000
		})
	})
}

$(function() {

})