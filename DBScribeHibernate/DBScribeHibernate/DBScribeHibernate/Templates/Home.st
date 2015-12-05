<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>DBScribe Report</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
   <script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js?lang=css&skin=desert"> </script>
 <style>
.modal.modal-wide .modal-dialog {
  width: 90%;
}
.modal-wide .modal-body {
  overflow-y: auto;
}
p {word-wrap: break-word; }
</style>
<script>
\$(".modal-wide").on("show.bs.modal", function() {
  var height = \$(window).height() - 200;
  \$(this).find(".modal-body").css("max-height", height);
});
</script>
<style>
div.tooltip-inner {
    max-width: 1500px;
}
</style>
<script>
\$(document).ready(function () {
  \$("a").tooltip({
    'selector': '',
    'placement': 'bottom',
    'container':'body'
  });
});
</script>
 </head>
 <body>

<div class="container">
	<div class="row">
		<div class="col-xs-3" style="position:fixed;float:left;left:0px">
			<div>
			<h2> DBScribe Report for $ProjName$</h2> <br/>
			There are $NUM_DB_METHODS$ database-related methods (out of $NUM_TOTAL_METHODS$ methods). 
			Among them, there are $NUM_SQL_OPERATING$ SQL Operating Methods,
			$NUM_LOCAL$ Local SQL Methods, and $NUM_DELEGATED$ Delegated SQL Methods.
			</div>
			<br/>

			<div class="panel panel-default">
			<div class="panel-heading">Methods</div><div class="panel-body" style="max-height:600px;overflow-x: scroll;overflow-y: scroll">
			$IDNum,
			MethodLinkID:
			{mid, ms| 
			<a href="#$mid$">$ms$()</a><br>
			}
			$
			
			</div>
			</div>
		</div>
		<div class="col-xs-9" style="float:right;right:0px">
		
			$IDNum,
			MethodSignature,
			MethodBodyDesc:
			{mid, ms, mbd| 
			<div class="panel panel-primary"><a id="$mid$"></a>
				<div class="panel-heading"><b>$ms$</b></div>
				<div class="panel-body">				
				<p>
				<!-- <span class="glyphicon glyphicon-comment"></span> -->
				$mbd$
				</p>
				</div>
			</div>
			}
			$
		</div>
	</div>
</div>
<script>
\$(document).ready(function(){
    \$('[data-toggle="tooltip"]').tooltip();   
});
</script>
</body>
</html>