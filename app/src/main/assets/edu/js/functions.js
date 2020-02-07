// When DOM is fully loaded
function changepage(url, title){
	window.setTimeout("window.location.href='"+url+"';",100);
	//window.location.href="webview://settitle:"+title;
}


// When DOM is fully loaded
jQuery(document).ready(function($) {

	/* ---------------------------------------------------------------------- */
	/*	Tips Popup
	/* ---------------------------------------------------------------------- */
	$(".tips_pop").hide();
	$(".tips_box").hide();
	     $(".tips_img").click(function(){	
		       $(this).next(".tips_box").slideToggle("fast");								 
		});
	    
	     $(".tips").click(function(){	
		       $(this).next(".tips_pop").slideToggle("fast");								 
		});
		 
	     $(".tips_box").click(function(){	
		       $(this).hide("fast");								 
		});
		 
		  $(".tips_pop").click(function(){	
		       $(this).hide("fast");								 
		});
	/* end Tips Popup */	
	
	
	/* ---------------------------------------------------------------------- */
	/*	Quiz
	/* ---------------------------------------------------------------------- */
	//$(".anstext").hide();
    $(".ansid li").click(function() {	
							     $(this).parents(".ansid").next(".anstext").slideDown("fast").css("display","block");	
								 $(this).parents(".ansid").find(".qrst").addClass("active");	
								
								var checkAns =  $(this).parents(".ansid").next(".anstext").find("h5");
								
								if ($(this).hasClass("awrong"))   {												 
												 checkAns.addClass("wrong");	
												 
								}else if (($(this).hasClass("acorrect"))&&(checkAns.hasClass("correct")) ){
											    checkAns.addClass("correct");	
												
								 }else if (($(this).hasClass("acorrect"))&&(checkAns.hasClass("wrong")) ){
											    checkAns.addClass("wrong");									 			 
								 
								 }else if ($(this).hasClass("acorrect"))   {												 
												 checkAns.addClass("correct");
								  								 
								 }else {     
										        checkAns.addClass("wrong");
							     }	
								  return false;			
						});
		 
	/* end Quiz */	
	
	
	/* ---------------------------------------------------------------------- */
	/*	Dropdown List
	/* ---------------------------------------------------------------------- */
		  $(".ddslick b").click(function(){	
		       $(this).next("ul.ddlist").slideToggle("fast");							 
		  });	  
		  
		  $(".ddlist li").click(function(){	
				var str = $(this).text();
				
				$(".ddlist li").removeClass("selected");	
		        $(this).addClass("selected");	
				$(".ddslick span#landing").html(str);
				
				$(this).parents("ul.ddlist").slideUp("fast");					
						      
			   $(".tbRow li.tbCol").removeClass("active");
				   if ($(this).hasClass('dd2'))  {			
					    $(".tbRow li.tbCol:nth-child(3)").addClass("active");										
			      }else if ($(this).hasClass('dd3')){
					    $(".tbRow li.tbCol:nth-child(4)").addClass("active");						
				  }else if ($(this).hasClass('dd4')){
					   $(".tbRow li.tbCol:nth-child(5)").addClass("active");			
				  } else{
					   $(".tbRow li.tbCol:nth-child(2)").addClass("active");								
				  }	
  
		  });
		  
    /* end Dropdown List */	
	
	/* ---------------------------------------------------------------------- */
	/*	Glossary List
	/* ---------------------------------------------------------------------- */
	 $(".glossary .tit").click(function(){	
		       $(this).parents("li").find(".detail").slideToggle("500");	
			   $(this).toggleClass('active');
	           return false;		
		});
	
    /* end Glossary List */		
	
	/* ---------------------------------------------------------------------- */
	/*	FAQ List
	/* ---------------------------------------------------------------------- */
	 $(".faq li .tit").click(function(){	
		       $(this).parents("li").find(".detail").slideToggle("500");	
			   $(this).toggleClass('active');
	           return false;			   
		});
	 
	 $(".faq li .collapse").click(function(){	
		       $(this).parents("li .detail").slideUp("fast");		
			   $(this).parents("li").find(".tit").removeClass("active");		
	           return false;			   
		});
	
    /* end FAQ List */		
	
	
	/* ---------------------------------------------------------------------- */
	/*	Back to top
	/* ---------------------------------------------------------------------- */
	(function() {
			oldiOS     = false,
			oldAndroid = false;
		// Detect if older iOS device, which doesn't support fixed position
		if( /(iPhone|iPod|iPad)\sOS\s[0-4][_\d]+/i.test(navigator.userAgent) )
			oldiOS = true;
		// Detect if older Android device, which doesn't support fixed position
		if( /Android\s+([0-2][\.\d]+)/i.test(navigator.userAgent) )
			oldAndroid = true;
			
		$('#back-to-top').click(function( e ){
				$('html, body').animate({ scrollTop : 0 }, 500 );
				e.preventDefault();
			});

		$(window).scroll(function() {
			var position = $(window).scrollTop();
			if( oldiOS || oldAndroid ) {
				$( settings.button ).css({
					'position' : 'absolute',
					'top'      : position + $(window).height()
				});
			}
			if ( position > 150 ) 
				$( '#back-to-top' ).show( 300 );
			else 
				$( '#back-to-top' ).hide();
		});

	})();
	/* end UItoTop (Back to Top) */
	
    /* end Back to top */		
	
});