$(function(){
			var countyS = document.getElementById("county");
			var areaS = document.getElementById("area");

			$.getJSON("/BentoDelivery/Datas/taiwan.json", {}, function(data) {
				if (countyS) {
									$.each(data, function() {
					for (var i = 0; i < data.Taiwan.length; i++) {
						var eleO = document.createElement("option");
						eleO.setAttribute("value",i);
						var txt = document.createTextNode(data.Taiwan[i].County);
						eleO.appendChild(txt);
						countyS.appendChild(eleO);
					}
				});

				countyS.addEventListener("change",getValue,false);

				function getValue (){
					$.each(data, function() {
						$(areaS).empty();
						var county = $("#county").val();
						for (var i = 0; i < data.Taiwan[county].Area.length; i++) {
							var eleO = document.createElement("option");
							eleO.setAttribute("value",i);
							var txt = document.createTextNode(data.Taiwan[county].Area[i]);
							eleO.appendChild(txt);
							areaS.appendChild(eleO);
							eleO.setAttribute("value", data.Taiwan[county].Area[i]);
						}
					});
				}
				}
				var countyS2 = document.getElementById("county2");
				var areaS2 = document.getElementById("area2");
				if (countyS2) {
									$.each(data, function() {
				for (var i2 = 0; i2 < data.Taiwan.length; i2++) {
					var eleO2 = document.createElement("option");
					eleO2.setAttribute("value",i2);
					var txt2 = document.createTextNode(data.Taiwan[i2].County);
					eleO2.appendChild(txt2);							
					countyS2.appendChild(eleO2);	
				}
			});
				countyS2.addEventListener("change",getValue2,false);
				function getValue2 (){
					$.each(data, function() {
						$(areaS2).empty();
						var county = $("#county2").val();
						for (var i = 0; i < data.Taiwan[county].Area.length; i++) {
							var eleO = document.createElement("option");
							eleO.setAttribute("value",i);
							var txt = document.createTextNode(data.Taiwan[county].Area[i]);
							eleO.appendChild(txt);
							areaS2.appendChild(eleO);
							eleO.setAttribute("value",data.Taiwan[county].Area[i]);								
						}					
					}); 
				}
				}
			});
			
		})