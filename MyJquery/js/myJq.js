 (function(window,undefined){
           	   
    var JQuery = (function(){
    var JQuery = function(selector,context){
        return new JQuery.fn.init(selector,context,rootJQuery;
    	},

    JQuery.fn = JQuery.prototype = {
               	   constructor:JQuery,
               	   init:function(selector,context,rootJQuery){}
    }

   JQuery.fn.init.prototype = JQuery.fn;

   JQuery.extend = JQuery.fn.extend = function(){}

   JQuery.extend({});

   return JQuery;

    })();

           	   window.JQuery = window.$ = JQuery;
})(window);