
/* jquery.ui.datepicker.min.js */

/* 1 */ /*! jQuery UI - v1.10.4 - 2014-01-17
/* 2 *| * http://jqueryui.com
/* 3 *| * Copyright 2014 jQuery Foundation and other contributors; Licensed MIT */
/* 4 */ (function(t,e){function i(){this._curInst=null,this._keyEvent=!1,this._disabledInputs=[],this._datepickerShowing=!1,this._inDialog=!1,this._mainDivId="ui-datepicker-div",this._inlineClass="ui-datepicker-inline",this._appendClass="ui-datepicker-append",this._triggerClass="ui-datepicker-trigger",this._dialogClass="ui-datepicker-dialog",this._disableClass="ui-datepicker-disabled",this._unselectableClass="ui-datepicker-unselectable",this._currentClass="ui-datepicker-current-day",this._dayOverClass="ui-datepicker-days-cell-over",this.regional=[],this.regional[""]={closeText:"Done",prevText:"Prev",nextText:"Next",currentText:"Today",monthNames:["January","February","March","April","May","June","July","August","September","October","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],dayNames:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],dayNamesShort:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],dayNamesMin:["Su","Mo","Tu","We","Th","Fr","Sa"],weekHeader:"Wk",dateFormat:"mm/dd/yy",firstDay:0,isRTL:!1,showMonthAfterYear:!1,yearSuffix:""},this._defaults={showOn:"focus",showAnim:"fadeIn",showOptions:{},defaultDate:null,appendText:"",buttonText:"...",buttonImage:"",buttonImageOnly:!1,hideIfNoPrevNext:!1,navigationAsDateFormat:!1,gotoCurrent:!1,changeMonth:!1,changeYear:!1,yearRange:"c-10:c+10",showOtherMonths:!1,selectOtherMonths:!1,showWeek:!1,calculateWeek:this.iso8601Week,shortYearCutoff:"+10",minDate:null,maxDate:null,duration:"fast",beforeShowDay:null,beforeShow:null,onSelect:null,onChangeMonthYear:null,onClose:null,numberOfMonths:1,showCurrentAtPos:0,stepMonths:1,stepBigMonths:12,altField:"",altFormat:"",constrainInput:!0,showButtonPanel:!1,autoSize:!1,disabled:!1},t.extend(this._defaults,this.regional[""]),this.dpDiv=s(t("<div id='"+this._mainDivId+"' class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>"))}function s(e){var i="button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a";return e.delegate(i,"mouseout",function(){t(this).removeClass("ui-state-hover"),-1!==this.className.indexOf("ui-datepicker-prev")&&t(this).removeClass("ui-datepicker-prev-hover"),-1!==this.className.indexOf("ui-datepicker-next")&&t(this).removeClass("ui-datepicker-next-hover")}).delegate(i,"mouseover",function(){t.datepicker._isDisabledDatepicker(a.inline?e.parent()[0]:a.input[0])||(t(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover"),t(this).addClass("ui-state-hover"),-1!==this.className.indexOf("ui-datepicker-prev")&&t(this).addClass("ui-datepicker-prev-hover"),-1!==this.className.indexOf("ui-datepicker-next")&&t(this).addClass("ui-datepicker-next-hover"))})}function n(e,i){t.extend(e,i);for(var s in i)null==i[s]&&(e[s]=i[s]);return e}t.extend(t.ui,{datepicker:{version:"1.10.4"}});var a,r="datepicker";t.extend(i.prototype,{markerClassName:"hasDatepicker",maxRows:4,_widgetDatepicker:function(){return this.dpDiv},setDefaults:function(t){return n(this._defaults,t||{}),this},_attachDatepicker:function(e,i){var s,n,a;s=e.nodeName.toLowerCase(),n="div"===s||"span"===s,e.id||(this.uuid+=1,e.id="dp"+this.uuid),a=this._newInst(t(e),n),a.settings=t.extend({},i||{}),"input"===s?this._connectDatepicker(e,a):n&&this._inlineDatepicker(e,a)},_newInst:function(e,i){var n=e[0].id.replace(/([^A-Za-z0-9_\-])/g,"\\\\$1");return{id:n,input:e,selectedDay:0,selectedMonth:0,selectedYear:0,drawMonth:0,drawYear:0,inline:i,dpDiv:i?s(t("<div class='"+this._inlineClass+" ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>")):this.dpDiv}},_connectDatepicker:function(e,i){var s=t(e);i.append=t([]),i.trigger=t([]),s.hasClass(this.markerClassName)||(this._attachments(s,i),s.addClass(this.markerClassName).keydown(this._doKeyDown).keypress(this._doKeyPress).keyup(this._doKeyUp),this._autoSize(i),t.data(e,r,i),i.settings.disabled&&this._disableDatepicker(e))},_attachments:function(e,i){var s,n,a,r=this._get(i,"appendText"),o=this._get(i,"isRTL");i.append&&i.append.remove(),r&&(i.append=t("<span class='"+this._appendClass+"'>"+r+"</span>"),e[o?"before":"after"](i.append)),e.unbind("focus",this._showDatepicker),i.trigger&&i.trigger.remove(),s=this._get(i,"showOn"),("focus"===s||"both"===s)&&e.focus(this._showDatepicker),("button"===s||"both"===s)&&(n=this._get(i,"buttonText"),a=this._get(i,"buttonImage"),i.trigger=t(this._get(i,"buttonImageOnly")?t("<img/>").addClass(this._triggerClass).attr({src:a,alt:n,title:n}):t("<button type='button'></button>").addClass(this._triggerClass).html(a?t("<img/>").attr({src:a,alt:n,title:n}):n)),e[o?"before":"after"](i.trigger),i.trigger.click(function(){return t.datepicker._datepickerShowing&&t.datepicker._lastInput===e[0]?t.datepicker._hideDatepicker():t.datepicker._datepickerShowing&&t.datepicker._lastInput!==e[0]?(t.datepicker._hideDatepicker(),t.datepicker._showDatepicker(e[0])):t.datepicker._showDatepicker(e[0]),!1}))},_autoSize:function(t){if(this._get(t,"autoSize")&&!t.inline){var e,i,s,n,a=new Date(2009,11,20),r=this._get(t,"dateFormat");r.match(/[DM]/)&&(e=function(t){for(i=0,s=0,n=0;t.length>n;n++)t[n].length>i&&(i=t[n].length,s=n);return s},a.setMonth(e(this._get(t,r.match(/MM/)?"monthNames":"monthNamesShort"))),a.setDate(e(this._get(t,r.match(/DD/)?"dayNames":"dayNamesShort"))+20-a.getDay())),t.input.attr("size",this._formatDate(t,a).length)}},_inlineDatepicker:function(e,i){var s=t(e);s.hasClass(this.markerClassName)||(s.addClass(this.markerClassName).append(i.dpDiv),t.data(e,r,i),this._setDate(i,this._getDefaultDate(i),!0),this._updateDatepicker(i),this._updateAlternate(i),i.settings.disabled&&this._disableDatepicker(e),i.dpDiv.css("display","block"))},_dialogDatepicker:function(e,i,s,a,o){var h,l,c,u,d,p=this._dialogInst;return p||(this.uuid+=1,h="dp"+this.uuid,this._dialogInput=t("<input type='text' id='"+h+"' style='position: absolute; top: -100px; width: 0px;'/>"),this._dialogInput.keydown(this._doKeyDown),t("body").append(this._dialogInput),p=this._dialogInst=this._newInst(this._dialogInput,!1),p.settings={},t.data(this._dialogInput[0],r,p)),n(p.settings,a||{}),i=i&&i.constructor===Date?this._formatDate(p,i):i,this._dialogInput.val(i),this._pos=o?o.length?o:[o.pageX,o.pageY]:null,this._pos||(l=document.documentElement.clientWidth,c=document.documentElement.clientHeight,u=document.documentElement.scrollLeft||document.body.scrollLeft,d=document.documentElement.scrollTop||document.body.scrollTop,this._pos=[l/2-100+u,c/2-150+d]),this._dialogInput.css("left",this._pos[0]+20+"px").css("top",this._pos[1]+"px"),p.settings.onSelect=s,this._inDialog=!0,this.dpDiv.addClass(this._dialogClass),this._showDatepicker(this._dialogInput[0]),t.blockUI&&t.blockUI(this.dpDiv),t.data(this._dialogInput[0],r,p),this},_destroyDatepicker:function(e){var i,s=t(e),n=t.data(e,r);s.hasClass(this.markerClassName)&&(i=e.nodeName.toLowerCase(),t.removeData(e,r),"input"===i?(n.append.remove(),n.trigger.remove(),s.removeClass(this.markerClassName).unbind("focus",this._showDatepicker).unbind("keydown",this._doKeyDown).unbind("keypress",this._doKeyPress).unbind("keyup",this._doKeyUp)):("div"===i||"span"===i)&&s.removeClass(this.markerClassName).empty())},_enableDatepicker:function(e){var i,s,n=t(e),a=t.data(e,r);n.hasClass(this.markerClassName)&&(i=e.nodeName.toLowerCase(),"input"===i?(e.disabled=!1,a.trigger.filter("button").each(function(){this.disabled=!1}).end().filter("img").css({opacity:"1.0",cursor:""})):("div"===i||"span"===i)&&(s=n.children("."+this._inlineClass),s.children().removeClass("ui-state-disabled"),s.find("select.ui-datepicker-month, select.ui-datepicker-year").prop("disabled",!1)),this._disabledInputs=t.map(this._disabledInputs,function(t){return t===e?null:t}))},_disableDatepicker:function(e){var i,s,n=t(e),a=t.data(e,r);n.hasClass(this.markerClassName)&&(i=e.nodeName.toLowerCase(),"input"===i?(e.disabled=!0,a.trigger.filter("button").each(function(){this.disabled=!0}).end().filter("img").css({opacity:"0.5",cursor:"default"})):("div"===i||"span"===i)&&(s=n.children("."+this._inlineClass),s.children().addClass("ui-state-disabled"),s.find("select.ui-datepicker-month, select.ui-datepicker-year").prop("disabled",!0)),this._disabledInputs=t.map(this._disabledInputs,function(t){return t===e?null:t}),this._disabledInputs[this._disabledInputs.length]=e)},_isDisabledDatepicker:function(t){if(!t)return!1;for(var e=0;this._disabledInputs.length>e;e++)if(this._disabledInputs[e]===t)return!0;return!1},_getInst:function(e){try{return t.data(e,r)}catch(i){throw"Missing instance data for this datepicker"}},_optionDatepicker:function(i,s,a){var r,o,h,l,c=this._getInst(i);return 2===arguments.length&&"string"==typeof s?"defaults"===s?t.extend({},t.datepicker._defaults):c?"all"===s?t.extend({},c.settings):this._get(c,s):null:(r=s||{},"string"==typeof s&&(r={},r[s]=a),c&&(this._curInst===c&&this._hideDatepicker(),o=this._getDateDatepicker(i,!0),h=this._getMinMaxDate(c,"min"),l=this._getMinMaxDate(c,"max"),n(c.settings,r),null!==h&&r.dateFormat!==e&&r.minDate===e&&(c.settings.minDate=this._formatDate(c,h)),null!==l&&r.dateFormat!==e&&r.maxDate===e&&(c.settings.maxDate=this._formatDate(c,l)),"disabled"in r&&(r.disabled?this._disableDatepicker(i):this._enableDatepicker(i)),this._attachments(t(i),c),this._autoSize(c),this._setDate(c,o),this._updateAlternate(c),this._updateDatepicker(c)),e)},_changeDatepicker:function(t,e,i){this._optionDatepicker(t,e,i)},_refreshDatepicker:function(t){var e=this._getInst(t);e&&this._updateDatepicker(e)},_setDateDatepicker:function(t,e){var i=this._getInst(t);i&&(this._setDate(i,e),this._updateDatepicker(i),this._updateAlternate(i))},_getDateDatepicker:function(t,e){var i=this._getInst(t);return i&&!i.inline&&this._setDateFromField(i,e),i?this._getDate(i):null},_doKeyDown:function(e){var i,s,n,a=t.datepicker._getInst(e.target),r=!0,o=a.dpDiv.is(".ui-datepicker-rtl");if(a._keyEvent=!0,t.datepicker._datepickerShowing)switch(e.keyCode){case 9:t.datepicker._hideDatepicker(),r=!1;break;case 13:return n=t("td."+t.datepicker._dayOverClass+":not(."+t.datepicker._currentClass+")",a.dpDiv),n[0]&&t.datepicker._selectDay(e.target,a.selectedMonth,a.selectedYear,n[0]),i=t.datepicker._get(a,"onSelect"),i?(s=t.datepicker._formatDate(a),i.apply(a.input?a.input[0]:null,[s,a])):t.datepicker._hideDatepicker(),!1;case 27:t.datepicker._hideDatepicker();break;case 33:t.datepicker._adjustDate(e.target,e.ctrlKey?-t.datepicker._get(a,"stepBigMonths"):-t.datepicker._get(a,"stepMonths"),"M");break;case 34:t.datepicker._adjustDate(e.target,e.ctrlKey?+t.datepicker._get(a,"stepBigMonths"):+t.datepicker._get(a,"stepMonths"),"M");break;case 35:(e.ctrlKey||e.metaKey)&&t.datepicker._clearDate(e.target),r=e.ctrlKey||e.metaKey;break;case 36:(e.ctrlKey||e.metaKey)&&t.datepicker._gotoToday(e.target),r=e.ctrlKey||e.metaKey;break;case 37:(e.ctrlKey||e.metaKey)&&t.datepicker._adjustDate(e.target,o?1:-1,"D"),r=e.ctrlKey||e.metaKey,e.originalEvent.altKey&&t.datepicker._adjustDate(e.target,e.ctrlKey?-t.datepicker._get(a,"stepBigMonths"):-t.datepicker._get(a,"stepMonths"),"M");break;case 38:(e.ctrlKey||e.metaKey)&&t.datepicker._adjustDate(e.target,-7,"D"),r=e.ctrlKey||e.metaKey;break;case 39:(e.ctrlKey||e.metaKey)&&t.datepicker._adjustDate(e.target,o?-1:1,"D"),r=e.ctrlKey||e.metaKey,e.originalEvent.altKey&&t.datepicker._adjustDate(e.target,e.ctrlKey?+t.datepicker._get(a,"stepBigMonths"):+t.datepicker._get(a,"stepMonths"),"M");break;case 40:(e.ctrlKey||e.metaKey)&&t.datepicker._adjustDate(e.target,7,"D"),r=e.ctrlKey||e.metaKey;break;default:r=!1}else 36===e.keyCode&&e.ctrlKey?t.datepicker._showDatepicker(this):r=!1;r&&(e.preventDefault(),e.stopPropagation())},_doKeyPress:function(i){var s,n,a=t.datepicker._getInst(i.target);return t.datepicker._get(a,"constrainInput")?(s=t.datepicker._possibleChars(t.datepicker._get(a,"dateFormat")),n=String.fromCharCode(null==i.charCode?i.keyCode:i.charCode),i.ctrlKey||i.metaKey||" ">n||!s||s.indexOf(n)>-1):e},_doKeyUp:function(e){var i,s=t.datepicker._getInst(e.target);if(s.input.val()!==s.lastVal)try{i=t.datepicker.parseDate(t.datepicker._get(s,"dateFormat"),s.input?s.input.val():null,t.datepicker._getFormatConfig(s)),i&&(t.datepicker._setDateFromField(s),t.datepicker._updateAlternate(s),t.datepicker._updateDatepicker(s))}catch(n){}return!0},_showDatepicker:function(e){if(e=e.target||e,"input"!==e.nodeName.toLowerCase()&&(e=t("input",e.parentNode)[0]),!t.datepicker._isDisabledDatepicker(e)&&t.datepicker._lastInput!==e){var i,s,a,r,o,h,l;i=t.datepicker._getInst(e),t.datepicker._curInst&&t.datepicker._curInst!==i&&(t.datepicker._curInst.dpDiv.stop(!0,!0),i&&t.datepicker._datepickerShowing&&t.datepicker._hideDatepicker(t.datepicker._curInst.input[0])),s=t.datepicker._get(i,"beforeShow"),a=s?s.apply(e,[e,i]):{},a!==!1&&(n(i.settings,a),i.lastVal=null,t.datepicker._lastInput=e,t.datepicker._setDateFromField(i),t.datepicker._inDialog&&(e.value=""),t.datepicker._pos||(t.datepicker._pos=t.datepicker._findPos(e),t.datepicker._pos[1]+=e.offsetHeight),r=!1,t(e).parents().each(function(){return r|="fixed"===t(this).css("position"),!r}),o={left:t.datepicker._pos[0],top:t.datepicker._pos[1]},t.datepicker._pos=null,i.dpDiv.empty(),i.dpDiv.css({position:"absolute",display:"block",top:"-1000px"}),t.datepicker._updateDatepicker(i),o=t.datepicker._checkOffset(i,o,r),i.dpDiv.css({position:t.datepicker._inDialog&&t.blockUI?"static":r?"fixed":"absolute",display:"none",left:o.left+"px",top:o.top+"px"}),i.inline||(h=t.datepicker._get(i,"showAnim"),l=t.datepicker._get(i,"duration"),i.dpDiv.zIndex(t(e).zIndex()+1),t.datepicker._datepickerShowing=!0,t.effects&&t.effects.effect[h]?i.dpDiv.show(h,t.datepicker._get(i,"showOptions"),l):i.dpDiv[h||"show"](h?l:null),t.datepicker._shouldFocusInput(i)&&i.input.focus(),t.datepicker._curInst=i))}},_updateDatepicker:function(e){this.maxRows=4,a=e,e.dpDiv.empty().append(this._generateHTML(e)),this._attachHandlers(e),e.dpDiv.find("."+this._dayOverClass+" a").mouseover();var i,s=this._getNumberOfMonths(e),n=s[1],r=17;e.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width(""),n>1&&e.dpDiv.addClass("ui-datepicker-multi-"+n).css("width",r*n+"em"),e.dpDiv[(1!==s[0]||1!==s[1]?"add":"remove")+"Class"]("ui-datepicker-multi"),e.dpDiv[(this._get(e,"isRTL")?"add":"remove")+"Class"]("ui-datepicker-rtl"),e===t.datepicker._curInst&&t.datepicker._datepickerShowing&&t.datepicker._shouldFocusInput(e)&&e.input.focus(),e.yearshtml&&(i=e.yearshtml,setTimeout(function(){i===e.yearshtml&&e.yearshtml&&e.dpDiv.find("select.ui-datepicker-year:first").replaceWith(e.yearshtml),i=e.yearshtml=null},0))},_shouldFocusInput:function(t){return t.input&&t.input.is(":visible")&&!t.input.is(":disabled")&&!t.input.is(":focus")},_checkOffset:function(e,i,s){var n=e.dpDiv.outerWidth(),a=e.dpDiv.outerHeight(),r=e.input?e.input.outerWidth():0,o=e.input?e.input.outerHeight():0,h=document.documentElement.clientWidth+(s?0:t(document).scrollLeft()),l=document.documentElement.clientHeight+(s?0:t(document).scrollTop());return i.left-=this._get(e,"isRTL")?n-r:0,i.left-=s&&i.left===e.input.offset().left?t(document).scrollLeft():0,i.top-=s&&i.top===e.input.offset().top+o?t(document).scrollTop():0,i.left-=Math.min(i.left,i.left+n>h&&h>n?Math.abs(i.left+n-h):0),i.top-=Math.min(i.top,i.top+a>l&&l>a?Math.abs(a+o):0),i},_findPos:function(e){for(var i,s=this._getInst(e),n=this._get(s,"isRTL");e&&("hidden"===e.type||1!==e.nodeType||t.expr.filters.hidden(e));)e=e[n?"previousSibling":"nextSibling"];return i=t(e).offset(),[i.left,i.top]},_hideDatepicker:function(e){var i,s,n,a,o=this._curInst;!o||e&&o!==t.data(e,r)||this._datepickerShowing&&(i=this._get(o,"showAnim"),s=this._get(o,"duration"),n=function(){t.datepicker._tidyDialog(o)},t.effects&&(t.effects.effect[i]||t.effects[i])?o.dpDiv.hide(i,t.datepicker._get(o,"showOptions"),s,n):o.dpDiv["slideDown"===i?"slideUp":"fadeIn"===i?"fadeOut":"hide"](i?s:null,n),i||n(),this._datepickerShowing=!1,a=this._get(o,"onClose"),a&&a.apply(o.input?o.input[0]:null,[o.input?o.input.val():"",o]),this._lastInput=null,this._inDialog&&(this._dialogInput.css({position:"absolute",left:"0",top:"-100px"}),t.blockUI&&(t.unblockUI(),t("body").append(this.dpDiv))),this._inDialog=!1)},_tidyDialog:function(t){t.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar")},_checkExternalClick:function(e){if(t.datepicker._curInst){var i=t(e.target),s=t.datepicker._getInst(i[0]);(i[0].id!==t.datepicker._mainDivId&&0===i.parents("#"+t.datepicker._mainDivId).length&&!i.hasClass(t.datepicker.markerClassName)&&!i.closest("."+t.datepicker._triggerClass).length&&t.datepicker._datepickerShowing&&(!t.datepicker._inDialog||!t.blockUI)||i.hasClass(t.datepicker.markerClassName)&&t.datepicker._curInst!==s)&&t.datepicker._hideDatepicker()}},_adjustDate:function(e,i,s){var n=t(e),a=this._getInst(n[0]);this._isDisabledDatepicker(n[0])||(this._adjustInstDate(a,i+("M"===s?this._get(a,"showCurrentAtPos"):0),s),this._updateDatepicker(a))},_gotoToday:function(e){var i,s=t(e),n=this._getInst(s[0]);this._get(n,"gotoCurrent")&&n.currentDay?(n.selectedDay=n.currentDay,n.drawMonth=n.selectedMonth=n.currentMonth,n.drawYear=n.selectedYear=n.currentYear):(i=new Date,n.selectedDay=i.getDate(),n.drawMonth=n.selectedMonth=i.getMonth(),n.drawYear=n.selectedYear=i.getFullYear()),this._notifyChange(n),this._adjustDate(s)},_selectMonthYear:function(e,i,s){var n=t(e),a=this._getInst(n[0]);a["selected"+("M"===s?"Month":"Year")]=a["draw"+("M"===s?"Month":"Year")]=parseInt(i.options[i.selectedIndex].value,10),this._notifyChange(a),this._adjustDate(n)},_selectDay:function(e,i,s,n){var a,r=t(e);t(n).hasClass(this._unselectableClass)||this._isDisabledDatepicker(r[0])||(a=this._getInst(r[0]),a.selectedDay=a.currentDay=t("a",n).html(),a.selectedMonth=a.currentMonth=i,a.selectedYear=a.currentYear=s,this._selectDate(e,this._formatDate(a,a.currentDay,a.currentMonth,a.currentYear)))},_clearDate:function(e){var i=t(e);this._selectDate(i,"")},_selectDate:function(e,i){var s,n=t(e),a=this._getInst(n[0]);i=null!=i?i:this._formatDate(a),a.input&&a.input.val(i),this._updateAlternate(a),s=this._get(a,"onSelect"),s?s.apply(a.input?a.input[0]:null,[i,a]):a.input&&a.input.trigger("change"),a.inline?this._updateDatepicker(a):(this._hideDatepicker(),this._lastInput=a.input[0],"object"!=typeof a.input[0]&&a.input.focus(),this._lastInput=null)},_updateAlternate:function(e){var i,s,n,a=this._get(e,"altField");a&&(i=this._get(e,"altFormat")||this._get(e,"dateFormat"),s=this._getDate(e),n=this.formatDate(i,s,this._getFormatConfig(e)),t(a).each(function(){t(this).val(n)}))},noWeekends:function(t){var e=t.getDay();return[e>0&&6>e,""]},iso8601Week:function(t){var e,i=new Date(t.getTime());return i.setDate(i.getDate()+4-(i.getDay()||7)),e=i.getTime(),i.setMonth(0),i.setDate(1),Math.floor(Math.round((e-i)/864e5)/7)+1},parseDate:function(i,s,n){if(null==i||null==s)throw"Invalid arguments";if(s="object"==typeof s?""+s:s+"",""===s)return null;var a,r,o,h,l=0,c=(n?n.shortYearCutoff:null)||this._defaults.shortYearCutoff,u="string"!=typeof c?c:(new Date).getFullYear()%100+parseInt(c,10),d=(n?n.dayNamesShort:null)||this._defaults.dayNamesShort,p=(n?n.dayNames:null)||this._defaults.dayNames,f=(n?n.monthNamesShort:null)||this._defaults.monthNamesShort,m=(n?n.monthNames:null)||this._defaults.monthNames,g=-1,v=-1,_=-1,b=-1,y=!1,x=function(t){var e=i.length>a+1&&i.charAt(a+1)===t;return e&&a++,e},k=function(t){var e=x(t),i="@"===t?14:"!"===t?20:"y"===t&&e?4:"o"===t?3:2,n=RegExp("^\\d{1,"+i+"}"),a=s.substring(l).match(n);if(!a)throw"Missing number at position "+l;return l+=a[0].length,parseInt(a[0],10)},w=function(i,n,a){var r=-1,o=t.map(x(i)?a:n,function(t,e){return[[e,t]]}).sort(function(t,e){return-(t[1].length-e[1].length)});if(t.each(o,function(t,i){var n=i[1];return s.substr(l,n.length).toLowerCase()===n.toLowerCase()?(r=i[0],l+=n.length,!1):e}),-1!==r)return r+1;throw"Unknown name at position "+l},D=function(){if(s.charAt(l)!==i.charAt(a))throw"Unexpected literal at position "+l;l++};for(a=0;i.length>a;a++)if(y)"'"!==i.charAt(a)||x("'")?D():y=!1;else switch(i.charAt(a)){case"d":_=k("d");break;case"D":w("D",d,p);break;case"o":b=k("o");break;case"m":v=k("m");break;case"M":v=w("M",f,m);break;case"y":g=k("y");break;case"@":h=new Date(k("@")),g=h.getFullYear(),v=h.getMonth()+1,_=h.getDate();break;case"!":h=new Date((k("!")-this._ticksTo1970)/1e4),g=h.getFullYear(),v=h.getMonth()+1,_=h.getDate();break;case"'":x("'")?D():y=!0;break;default:D()}if(s.length>l&&(o=s.substr(l),!/^\s+/.test(o)))throw"Extra/unparsed characters found in date: "+o;if(-1===g?g=(new Date).getFullYear():100>g&&(g+=(new Date).getFullYear()-(new Date).getFullYear()%100+(u>=g?0:-100)),b>-1)for(v=1,_=b;;){if(r=this._getDaysInMonth(g,v-1),r>=_)break;v++,_-=r}if(h=this._daylightSavingAdjust(new Date(g,v-1,_)),h.getFullYear()!==g||h.getMonth()+1!==v||h.getDate()!==_)throw"Invalid date";return h},ATOM:"yy-mm-dd",COOKIE:"D, dd M yy",ISO_8601:"yy-mm-dd",RFC_822:"D, d M y",RFC_850:"DD, dd-M-y",RFC_1036:"D, d M y",RFC_1123:"D, d M yy",RFC_2822:"D, d M yy",RSS:"D, d M y",TICKS:"!",TIMESTAMP:"@",W3C:"yy-mm-dd",_ticksTo1970:1e7*60*60*24*(718685+Math.floor(492.5)-Math.floor(19.7)+Math.floor(4.925)),formatDate:function(t,e,i){if(!e)return"";var s,n=(i?i.dayNamesShort:null)||this._defaults.dayNamesShort,a=(i?i.dayNames:null)||this._defaults.dayNames,r=(i?i.monthNamesShort:null)||this._defaults.monthNamesShort,o=(i?i.monthNames:null)||this._defaults.monthNames,h=function(e){var i=t.length>s+1&&t.charAt(s+1)===e;return i&&s++,i},l=function(t,e,i){var s=""+e;if(h(t))for(;i>s.length;)s="0"+s;return s},c=function(t,e,i,s){return h(t)?s[e]:i[e]},u="",d=!1;if(e)for(s=0;t.length>s;s++)if(d)"'"!==t.charAt(s)||h("'")?u+=t.charAt(s):d=!1;else switch(t.charAt(s)){case"d":u+=l("d",e.getDate(),2);break;case"D":u+=c("D",e.getDay(),n,a);break;case"o":u+=l("o",Math.round((new Date(e.getFullYear(),e.getMonth(),e.getDate()).getTime()-new Date(e.getFullYear(),0,0).getTime())/864e5),3);break;case"m":u+=l("m",e.getMonth()+1,2);break;case"M":u+=c("M",e.getMonth(),r,o);break;case"y":u+=h("y")?e.getFullYear():(10>e.getYear()%100?"0":"")+e.getYear()%100;break;case"@":u+=e.getTime();break;case"!":u+=1e4*e.getTime()+this._ticksTo1970;break;case"'":h("'")?u+="'":d=!0;break;default:u+=t.charAt(s)}return u},_possibleChars:function(t){var e,i="",s=!1,n=function(i){var s=t.length>e+1&&t.charAt(e+1)===i;return s&&e++,s};for(e=0;t.length>e;e++)if(s)"'"!==t.charAt(e)||n("'")?i+=t.charAt(e):s=!1;else switch(t.charAt(e)){case"d":case"m":case"y":case"@":i+="0123456789";break;case"D":case"M":return null;case"'":n("'")?i+="'":s=!0;break;default:i+=t.charAt(e)}return i},_get:function(t,i){return t.settings[i]!==e?t.settings[i]:this._defaults[i]},_setDateFromField:function(t,e){if(t.input.val()!==t.lastVal){var i=this._get(t,"dateFormat"),s=t.lastVal=t.input?t.input.val():null,n=this._getDefaultDate(t),a=n,r=this._getFormatConfig(t);try{a=this.parseDate(i,s,r)||n}catch(o){s=e?"":s}t.selectedDay=a.getDate(),t.drawMonth=t.selectedMonth=a.getMonth(),t.drawYear=t.selectedYear=a.getFullYear(),t.currentDay=s?a.getDate():0,t.currentMonth=s?a.getMonth():0,t.currentYear=s?a.getFullYear():0,this._adjustInstDate(t)}},_getDefaultDate:function(t){return this._restrictMinMax(t,this._determineDate(t,this._get(t,"defaultDate"),new Date))},_determineDate:function(e,i,s){var n=function(t){var e=new Date;return e.setDate(e.getDate()+t),e},a=function(i){try{return t.datepicker.parseDate(t.datepicker._get(e,"dateFormat"),i,t.datepicker._getFormatConfig(e))}catch(s){}for(var n=(i.toLowerCase().match(/^c/)?t.datepicker._getDate(e):null)||new Date,a=n.getFullYear(),r=n.getMonth(),o=n.getDate(),h=/([+\-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,l=h.exec(i);l;){switch(l[2]||"d"){case"d":case"D":o+=parseInt(l[1],10);break;case"w":case"W":o+=7*parseInt(l[1],10);break;case"m":case"M":r+=parseInt(l[1],10),o=Math.min(o,t.datepicker._getDaysInMonth(a,r));break;case"y":case"Y":a+=parseInt(l[1],10),o=Math.min(o,t.datepicker._getDaysInMonth(a,r))}l=h.exec(i)}return new Date(a,r,o)},r=null==i||""===i?s:"string"==typeof i?a(i):"number"==typeof i?isNaN(i)?s:n(i):new Date(i.getTime());return r=r&&"Invalid Date"==""+r?s:r,r&&(r.setHours(0),r.setMinutes(0),r.setSeconds(0),r.setMilliseconds(0)),this._daylightSavingAdjust(r)},_daylightSavingAdjust:function(t){return t?(t.setHours(t.getHours()>12?t.getHours()+2:0),t):null},_setDate:function(t,e,i){var s=!e,n=t.selectedMonth,a=t.selectedYear,r=this._restrictMinMax(t,this._determineDate(t,e,new Date));t.selectedDay=t.currentDay=r.getDate(),t.drawMonth=t.selectedMonth=t.currentMonth=r.getMonth(),t.drawYear=t.selectedYear=t.currentYear=r.getFullYear(),n===t.selectedMonth&&a===t.selectedYear||i||this._notifyChange(t),this._adjustInstDate(t),t.input&&t.input.val(s?"":this._formatDate(t))},_getDate:function(t){var e=!t.currentYear||t.input&&""===t.input.val()?null:this._daylightSavingAdjust(new Date(t.currentYear,t.currentMonth,t.currentDay));return e},_attachHandlers:function(e){var i=this._get(e,"stepMonths"),s="#"+e.id.replace(/\\\\/g,"\\");e.dpDiv.find("[data-handler]").map(function(){var e={prev:function(){t.datepicker._adjustDate(s,-i,"M")},next:function(){t.datepicker._adjustDate(s,+i,"M")},hide:function(){t.datepicker._hideDatepicker()},today:function(){t.datepicker._gotoToday(s)},selectDay:function(){return t.datepicker._selectDay(s,+this.getAttribute("data-month"),+this.getAttribute("data-year"),this),!1},selectMonth:function(){return t.datepicker._selectMonthYear(s,this,"M"),!1},selectYear:function(){return t.datepicker._selectMonthYear(s,this,"Y"),!1}};t(this).bind(this.getAttribute("data-event"),e[this.getAttribute("data-handler")])})},_generateHTML:function(t){var e,i,s,n,a,r,o,h,l,c,u,d,p,f,m,g,v,_,b,y,x,k,w,D,T,C,S,M,N,I,P,A,z,H,E,F,O,j,W,R=new Date,L=this._daylightSavingAdjust(new Date(R.getFullYear(),R.getMonth(),R.getDate())),Y=this._get(t,"isRTL"),B=this._get(t,"showButtonPanel"),K=this._get(t,"hideIfNoPrevNext"),J=this._get(t,"navigationAsDateFormat"),Q=this._getNumberOfMonths(t),V=this._get(t,"showCurrentAtPos"),U=this._get(t,"stepMonths"),q=1!==Q[0]||1!==Q[1],X=this._daylightSavingAdjust(t.currentDay?new Date(t.currentYear,t.currentMonth,t.currentDay):new Date(9999,9,9)),G=this._getMinMaxDate(t,"min"),$=this._getMinMaxDate(t,"max"),Z=t.drawMonth-V,te=t.drawYear;if(0>Z&&(Z+=12,te--),$)for(e=this._daylightSavingAdjust(new Date($.getFullYear(),$.getMonth()-Q[0]*Q[1]+1,$.getDate())),e=G&&G>e?G:e;this._daylightSavingAdjust(new Date(te,Z,1))>e;)Z--,0>Z&&(Z=11,te--);for(t.drawMonth=Z,t.drawYear=te,i=this._get(t,"prevText"),i=J?this.formatDate(i,this._daylightSavingAdjust(new Date(te,Z-U,1)),this._getFormatConfig(t)):i,s=this._canAdjustMonth(t,-1,te,Z)?"<a class='ui-datepicker-prev ui-corner-all' data-handler='prev' data-event='click' title='"+i+"'><span class='ui-icon ui-icon-circle-triangle-"+(Y?"e":"w")+"'>"+i+"</span></a>":K?"":"<a class='ui-datepicker-prev ui-corner-all ui-state-disabled' title='"+i+"'><span class='ui-icon ui-icon-circle-triangle-"+(Y?"e":"w")+"'>"+i+"</span></a>",n=this._get(t,"nextText"),n=J?this.formatDate(n,this._daylightSavingAdjust(new Date(te,Z+U,1)),this._getFormatConfig(t)):n,a=this._canAdjustMonth(t,1,te,Z)?"<a class='ui-datepicker-next ui-corner-all' data-handler='next' data-event='click' title='"+n+"'><span class='ui-icon ui-icon-circle-triangle-"+(Y?"w":"e")+"'>"+n+"</span></a>":K?"":"<a class='ui-datepicker-next ui-corner-all ui-state-disabled' title='"+n+"'><span class='ui-icon ui-icon-circle-triangle-"+(Y?"w":"e")+"'>"+n+"</span></a>",r=this._get(t,"currentText"),o=this._get(t,"gotoCurrent")&&t.currentDay?X:L,r=J?this.formatDate(r,o,this._getFormatConfig(t)):r,h=t.inline?"":"<button type='button' class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all' data-handler='hide' data-event='click'>"+this._get(t,"closeText")+"</button>",l=B?"<div class='ui-datepicker-buttonpane ui-widget-content'>"+(Y?h:"")+(this._isInRange(t,o)?"<button type='button' class='ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all' data-handler='today' data-event='click'>"+r+"</button>":"")+(Y?"":h)+"</div>":"",c=parseInt(this._get(t,"firstDay"),10),c=isNaN(c)?0:c,u=this._get(t,"showWeek"),d=this._get(t,"dayNames"),p=this._get(t,"dayNamesMin"),f=this._get(t,"monthNames"),m=this._get(t,"monthNamesShort"),g=this._get(t,"beforeShowDay"),v=this._get(t,"showOtherMonths"),_=this._get(t,"selectOtherMonths"),b=this._getDefaultDate(t),y="",k=0;Q[0]>k;k++){for(w="",this.maxRows=4,D=0;Q[1]>D;D++){if(T=this._daylightSavingAdjust(new Date(te,Z,t.selectedDay)),C=" ui-corner-all",S="",q){if(S+="<div class='ui-datepicker-group",Q[1]>1)switch(D){case 0:S+=" ui-datepicker-group-first",C=" ui-corner-"+(Y?"right":"left");break;case Q[1]-1:S+=" ui-datepicker-group-last",C=" ui-corner-"+(Y?"left":"right");break;default:S+=" ui-datepicker-group-middle",C=""}S+="'>"}for(S+="<div class='ui-datepicker-header ui-widget-header ui-helper-clearfix"+C+"'>"+(/all|left/.test(C)&&0===k?Y?a:s:"")+(/all|right/.test(C)&&0===k?Y?s:a:"")+this._generateMonthYearHeader(t,Z,te,G,$,k>0||D>0,f,m)+"</div><table class='ui-datepicker-calendar'><thead>"+"<tr>",M=u?"<th class='ui-datepicker-week-col'>"+this._get(t,"weekHeader")+"</th>":"",x=0;7>x;x++)N=(x+c)%7,M+="<th"+((x+c+6)%7>=5?" class='ui-datepicker-week-end'":"")+">"+"<span title='"+d[N]+"'>"+p[N]+"</span></th>";for(S+=M+"</tr></thead><tbody>",I=this._getDaysInMonth(te,Z),te===t.selectedYear&&Z===t.selectedMonth&&(t.selectedDay=Math.min(t.selectedDay,I)),P=(this._getFirstDayOfMonth(te,Z)-c+7)%7,A=Math.ceil((P+I)/7),z=q?this.maxRows>A?this.maxRows:A:A,this.maxRows=z,H=this._daylightSavingAdjust(new Date(te,Z,1-P)),E=0;z>E;E++){for(S+="<tr>",F=u?"<td class='ui-datepicker-week-col'>"+this._get(t,"calculateWeek")(H)+"</td>":"",x=0;7>x;x++)O=g?g.apply(t.input?t.input[0]:null,[H]):[!0,""],j=H.getMonth()!==Z,W=j&&!_||!O[0]||G&&G>H||$&&H>$,F+="<td class='"+((x+c+6)%7>=5?" ui-datepicker-week-end":"")+(j?" ui-datepicker-other-month":"")+(H.getTime()===T.getTime()&&Z===t.selectedMonth&&t._keyEvent||b.getTime()===H.getTime()&&b.getTime()===T.getTime()?" "+this._dayOverClass:"")+(W?" "+this._unselectableClass+" ui-state-disabled":"")+(j&&!v?"":" "+O[1]+(H.getTime()===X.getTime()?" "+this._currentClass:"")+(H.getTime()===L.getTime()?" ui-datepicker-today":""))+"'"+(j&&!v||!O[2]?"":" title='"+O[2].replace(/'/g,"&#39;")+"'")+(W?"":" data-handler='selectDay' data-event='click' data-month='"+H.getMonth()+"' data-year='"+H.getFullYear()+"'")+">"+(j&&!v?"&#xa0;":W?"<span class='ui-state-default'>"+H.getDate()+"</span>":"<a class='ui-state-default"+(H.getTime()===L.getTime()?" ui-state-highlight":"")+(H.getTime()===X.getTime()?" ui-state-active":"")+(j?" ui-priority-secondary":"")+"' href='#'>"+H.getDate()+"</a>")+"</td>",H.setDate(H.getDate()+1),H=this._daylightSavingAdjust(H);S+=F+"</tr>"}Z++,Z>11&&(Z=0,te++),S+="</tbody></table>"+(q?"</div>"+(Q[0]>0&&D===Q[1]-1?"<div class='ui-datepicker-row-break'></div>":""):""),w+=S}y+=w}return y+=l,t._keyEvent=!1,y},_generateMonthYearHeader:function(t,e,i,s,n,a,r,o){var h,l,c,u,d,p,f,m,g=this._get(t,"changeMonth"),v=this._get(t,"changeYear"),_=this._get(t,"showMonthAfterYear"),b="<div class='ui-datepicker-title'>",y="";if(a||!g)y+="<span class='ui-datepicker-month'>"+r[e]+"</span>";else{for(h=s&&s.getFullYear()===i,l=n&&n.getFullYear()===i,y+="<select class='ui-datepicker-month' data-handler='selectMonth' data-event='change'>",c=0;12>c;c++)(!h||c>=s.getMonth())&&(!l||n.getMonth()>=c)&&(y+="<option value='"+c+"'"+(c===e?" selected='selected'":"")+">"+o[c]+"</option>");y+="</select>"}if(_||(b+=y+(!a&&g&&v?"":"&#xa0;")),!t.yearshtml)if(t.yearshtml="",a||!v)b+="<span class='ui-datepicker-year'>"+i+"</span>";else{for(u=this._get(t,"yearRange").split(":"),d=(new Date).getFullYear(),p=function(t){var e=t.match(/c[+\-].*/)?i+parseInt(t.substring(1),10):t.match(/[+\-].*/)?d+parseInt(t,10):parseInt(t,10);
/* 5 */ return isNaN(e)?d:e},f=p(u[0]),m=Math.max(f,p(u[1]||"")),f=s?Math.max(f,s.getFullYear()):f,m=n?Math.min(m,n.getFullYear()):m,t.yearshtml+="<select class='ui-datepicker-year' data-handler='selectYear' data-event='change'>";m>=f;f++)t.yearshtml+="<option value='"+f+"'"+(f===i?" selected='selected'":"")+">"+f+"</option>";t.yearshtml+="</select>",b+=t.yearshtml,t.yearshtml=null}return b+=this._get(t,"yearSuffix"),_&&(b+=(!a&&g&&v?"":"&#xa0;")+y),b+="</div>"},_adjustInstDate:function(t,e,i){var s=t.drawYear+("Y"===i?e:0),n=t.drawMonth+("M"===i?e:0),a=Math.min(t.selectedDay,this._getDaysInMonth(s,n))+("D"===i?e:0),r=this._restrictMinMax(t,this._daylightSavingAdjust(new Date(s,n,a)));t.selectedDay=r.getDate(),t.drawMonth=t.selectedMonth=r.getMonth(),t.drawYear=t.selectedYear=r.getFullYear(),("M"===i||"Y"===i)&&this._notifyChange(t)},_restrictMinMax:function(t,e){var i=this._getMinMaxDate(t,"min"),s=this._getMinMaxDate(t,"max"),n=i&&i>e?i:e;return s&&n>s?s:n},_notifyChange:function(t){var e=this._get(t,"onChangeMonthYear");e&&e.apply(t.input?t.input[0]:null,[t.selectedYear,t.selectedMonth+1,t])},_getNumberOfMonths:function(t){var e=this._get(t,"numberOfMonths");return null==e?[1,1]:"number"==typeof e?[1,e]:e},_getMinMaxDate:function(t,e){return this._determineDate(t,this._get(t,e+"Date"),null)},_getDaysInMonth:function(t,e){return 32-this._daylightSavingAdjust(new Date(t,e,32)).getDate()},_getFirstDayOfMonth:function(t,e){return new Date(t,e,1).getDay()},_canAdjustMonth:function(t,e,i,s){var n=this._getNumberOfMonths(t),a=this._daylightSavingAdjust(new Date(i,s+(0>e?e:n[0]*n[1]),1));return 0>e&&a.setDate(this._getDaysInMonth(a.getFullYear(),a.getMonth())),this._isInRange(t,a)},_isInRange:function(t,e){var i,s,n=this._getMinMaxDate(t,"min"),a=this._getMinMaxDate(t,"max"),r=null,o=null,h=this._get(t,"yearRange");return h&&(i=h.split(":"),s=(new Date).getFullYear(),r=parseInt(i[0],10),o=parseInt(i[1],10),i[0].match(/[+\-].*/)&&(r+=s),i[1].match(/[+\-].*/)&&(o+=s)),(!n||e.getTime()>=n.getTime())&&(!a||e.getTime()<=a.getTime())&&(!r||e.getFullYear()>=r)&&(!o||o>=e.getFullYear())},_getFormatConfig:function(t){var e=this._get(t,"shortYearCutoff");return e="string"!=typeof e?e:(new Date).getFullYear()%100+parseInt(e,10),{shortYearCutoff:e,dayNamesShort:this._get(t,"dayNamesShort"),dayNames:this._get(t,"dayNames"),monthNamesShort:this._get(t,"monthNamesShort"),monthNames:this._get(t,"monthNames")}},_formatDate:function(t,e,i,s){e||(t.currentDay=t.selectedDay,t.currentMonth=t.selectedMonth,t.currentYear=t.selectedYear);var n=e?"object"==typeof e?e:this._daylightSavingAdjust(new Date(s,i,e)):this._daylightSavingAdjust(new Date(t.currentYear,t.currentMonth,t.currentDay));return this.formatDate(this._get(t,"dateFormat"),n,this._getFormatConfig(t))}}),t.fn.datepicker=function(e){if(!this.length)return this;t.datepicker.initialized||(t(document).mousedown(t.datepicker._checkExternalClick),t.datepicker.initialized=!0),0===t("#"+t.datepicker._mainDivId).length&&t("body").append(t.datepicker.dpDiv);var i=Array.prototype.slice.call(arguments,1);return"string"!=typeof e||"isDisabled"!==e&&"getDate"!==e&&"widget"!==e?"option"===e&&2===arguments.length&&"string"==typeof arguments[1]?t.datepicker["_"+e+"Datepicker"].apply(t.datepicker,[this[0]].concat(i)):this.each(function(){"string"==typeof e?t.datepicker["_"+e+"Datepicker"].apply(t.datepicker,[this].concat(i)):t.datepicker._attachDatepicker(this,e)}):t.datepicker["_"+e+"Datepicker"].apply(t.datepicker,[this[0]].concat(i))},t.datepicker=new i,t.datepicker.initialized=!1,t.datepicker.uuid=(new Date).getTime(),t.datepicker.version="1.10.4"})(jQuery);

;
/* wpv-date-front-end-control.js */

/* 1  */ jQuery(document).ready(function() {
/* 2  */ 	try {
/* 3  */ 		jQuery(".wpv-date-front-end").datepicker({
/* 4  */ 			onSelect: function(dateText, inst) {
/* 5  */ 				var control = this,
/* 6  */ 				url_param = jQuery(this).data('param');
/* 7  */ 				data = 'date=' + dateText;
/* 8  */ 				data += '&date-format=' + jQuery('.js-wpv-date-param-' + url_param + '-format').val();
/* 9  */ 				data += '&action=wpv_format_date';
/* 10 */ 				jQuery.post(front_ajaxurl, data, function(response) {
/* 11 */ 					response = jQuery.parseJSON(response);
/* 12 */ 					jQuery('.js-wpv-date-param-' + url_param + '-value').val(response['timestamp']);
/* 13 */ 					jQuery('.js-wpv-date-param-' + url_param).html(response['display']);
/* 14 */ 				});
/* 15 */ 			},
/* 16 */ 			dateFormat : 'ddmmyy',
/* 17 */ 			showOn: "button",
/* 18 */ 			buttonImage: wpv_calendar_image,
/* 19 */ 			buttonText: wpv_calendar_text,
/* 20 */ 			buttonImageOnly: true,
/* 21 */ 			changeMonth: true,
/* 22 */ 			changeYear: true
/* 23 */ 		});
/* 24 */ 	}
/* 25 */ 	catch (e) {
/* 26 */ 		
/* 27 */ 	}
/* 28 */ 	
/* 29 */ 	// TODO move this style to a unique frontend CSS file for Views
/* 30 */ 	jQuery("div.ui-datepicker").css('font-size', '12px');
/* 31 */ 	
/* 32 */ 	jQuery(document).on('click', '.js-wpv-date-display', function(){
/* 33 */ 		var url_param = jQuery(this).data('param');
/* 34 */ 		jQuery('.js-wpv-date-front-end-' + url_param).datepicker('show');
/* 35 */ 	});
/* 36 */ 	
/* 37 */ });

;
/* js_composer_front.js */

/* 1   */ document.documentElement.className += ' js_active ';
/* 2   */ document.documentElement.className += 'ontouchstart' in document.documentElement ? ' vc_mobile ' : ' vc_desktop ';
/* 3   */ (function(){
/* 4   */     var prefix = ['-webkit-','-o-','-moz-','-ms-',""];
/* 5   */     for (var i in prefix) { if(prefix[i]+'transform' in document.documentElement.style) document.documentElement.className += " vc_transform "; }
/* 6   */ })();
/* 7   */ /*
/* 8   *|    On document ready jQuery will fire set of functions.
/* 9   *|    If you want to override function behavior then copy it to your theme js file
/* 10  *|    with the same name.
/* 11  *| */
/* 12  */ 
/* 13  */ jQuery(window).load(function() {
/* 14  */ 
/* 15  */ 
/* 16  */ });
/* 17  */ var vc_js = function() {
/* 18  */   vc_twitterBehaviour();
/* 19  */   vc_toggleBehaviour();
/* 20  */   vc_tabsBehaviour();
/* 21  */   vc_accordionBehaviour();
/* 22  */   vc_teaserGrid();
/* 23  */   vc_carouselBehaviour();
/* 24  */   vc_slidersBehaviour();
/* 25  */   vc_prettyPhoto();
/* 26  */   vc_googleplus();
/* 27  */   vc_pinterest();
/* 28  */   vc_progress_bar();
/* 29  */   vc_waypoints();
/* 30  */   vc_plugin_flexslider();
/* 31  */ };
/* 32  */ jQuery(document).ready(function($) {
/* 33  */   window.vc_js();
/* 34  */ }); // END jQuery(document).ready
/* 35  */ 
/* 36  */ if ( typeof window['vc_plugin_flexslider'] !== 'function' ) {
/* 37  */  function vc_plugin_flexslider() {
/* 38  */    jQuery('.wpb_flexslider').each(function() {
/* 39  */      var this_element = jQuery(this);
/* 40  */      var sliderSpeed = 800,
/* 41  */        sliderTimeout = parseInt(this_element.attr('data-interval'))*1000,
/* 42  */        sliderFx = this_element.attr('data-flex_fx'),
/* 43  */        slideshow = true;
/* 44  */      if ( sliderTimeout == 0 ) slideshow = false;
/* 45  */ 
/* 46  */      this_element.flexslider({
/* 47  */        animation: sliderFx,
/* 48  */        slideshow: slideshow,
/* 49  */        slideshowSpeed: sliderTimeout,
/* 50  */        sliderSpeed: sliderSpeed,

/* js_composer_front.js */

/* 51  */        smoothHeight: true
/* 52  */      });
/* 53  */    });
/* 54  */  }
/* 55  */ }
/* 56  */ 
/* 57  */   /* Twitter
/* 58  *|  ---------------------------------------------------------- */
/* 59  */ if ( typeof window['vc_twitterBehaviour'] !== 'function' ) {
/* 60  */ 	function vc_twitterBehaviour() {
/* 61  */ 		jQuery('.wpb_twitter_widget .tweets').each(function(index) {
/* 62  */ 			var this_element = jQuery(this),
/* 63  */ 				tw_name = this_element.attr('data-tw_name');
/* 64  */ 				tw_count = this_element.attr('data-tw_count');
/* 65  */ 
/* 66  */ 			this_element.tweet({
/* 67  */ 				username: tw_name,
/* 68  */ 				join_text: "auto",
/* 69  */ 				avatar_size: 0,
/* 70  */ 				count: tw_count,
/* 71  */ 				template: "{avatar}{join}{text}{time}",
/* 72  */ 				auto_join_text_default: "",
/* 73  */ 				auto_join_text_ed: "",
/* 74  */ 				auto_join_text_ing: "",
/* 75  */ 				auto_join_text_reply: "",
/* 76  */ 				auto_join_text_url: "",
/* 77  */ 				loading_text: '<span class="loading_tweets">loading tweets...</span>'
/* 78  */ 	        });
/* 79  */ 		});
/* 80  */ 	}
/* 81  */ }
/* 82  */ 
/* 83  */ /* Google plus
/* 84  *| ---------------------------------------------------------- */
/* 85  */ if ( typeof window['vc_googleplus'] !== 'function' ) {
/* 86  */ 	function vc_googleplus() {
/* 87  */ 		if ( jQuery('.wpb_googleplus').length > 0 ) {
/* 88  */ 			(function() {
/* 89  */ 				var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
/* 90  */ 				po.src = 'https://apis.google.com/js/plusone.js';
/* 91  */ 				var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
/* 92  */ 			})();
/* 93  */ 		}
/* 94  */ 	}
/* 95  */ }
/* 96  */ 
/* 97  */ /* Pinterest
/* 98  *| ---------------------------------------------------------- */
/* 99  */ if ( typeof window['vc_pinterest'] !== 'function' ) {
/* 100 */ 	function vc_pinterest() {

/* js_composer_front.js */

/* 101 */ 		if ( jQuery('.wpb_pinterest').length > 0 ) {
/* 102 */ 			(function() {
/* 103 */ 				var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
/* 104 */ 				po.src = 'http://assets.pinterest.com/js/pinit.js';
/* 105 */ 				var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
/* 106 */ 				//<script type="text/javascript" src="//assets.pinterest.com/js/pinit.js"></script>
/* 107 */ 			})();
/* 108 */ 		}
/* 109 */ 	}
/* 110 */ }
/* 111 */ 
/* 112 */ /* Progress bar
/* 113 *| ---------------------------------------------------------- */
/* 114 */ if ( typeof window['vc_progress_bar'] !== 'function' ) {
/* 115 */   function vc_progress_bar() { 
/* 116 */     if (typeof jQuery.fn.waypoint !== 'undefined') {
/* 117 */ 
/* 118 */     	jQuery('.vc_progress_bar').waypoint(function() {
/* 119 */ 	  		jQuery(this).find('.vc_single_bar').each(function(index) {
/* 120 */ 	        var $this = jQuery(this),
/* 121 */ 	            bar = $this.find('.vc_bar'),
/* 122 */ 	            val = bar.data('percentage-value');
/* 123 */ 
/* 124 */ 	  		  setTimeout(function(){ bar.css({"width" : val+'%'}); }, index*200);
/* 125 */ 	    	});
/* 126 */ 		}, { offset: '85%' });
/* 127 */     }
/* 128 */   }
/* 129 */ }
/* 130 */ 
/* 131 */ /* Waypoints magic
/* 132 *| ---------------------------------------------------------- */
/* 133 */ if ( typeof window['vc_waypoints'] !== 'function' ) {
/* 134 */   function vc_waypoints() {
/* 135 */ 	if (typeof jQuery.fn.waypoint !== 'undefined') {
/* 136 */     jQuery('.wpb_animate_when_almost_visible:not(.wpb_start_animation)').waypoint(function() {
/* 137 */ 			jQuery(this).addClass('wpb_start_animation');
/* 138 */ 		}, { offset: '85%' });
/* 139 */ 	}
/* 140 */   }
/* 141 */ }
/* 142 */ 
/* 143 */ /* Toggle
/* 144 *| ---------------------------------------------------------- */
/* 145 */ if ( typeof window['vc_toggleBehaviour'] !== 'function' ) {
/* 146 */ 	function vc_toggleBehaviour() {
/* 147 */ 		jQuery(".wpb_toggle").unbind('click').click(function(e) {
/* 148 */       if(jQuery(this).next().is(':animated')) {
/* 149 */         return false;
/* 150 */       }

/* js_composer_front.js */

/* 151 */ 			if ( jQuery(this).hasClass('wpb_toggle_title_active') ) {
/* 152 */ 				jQuery(this).removeClass('wpb_toggle_title_active').next().slideUp(500);
/* 153 */ 			} else {
/* 154 */ 				jQuery(this).addClass('wpb_toggle_title_active').next().slideDown(500);
/* 155 */ 			}
/* 156 */ 		});
/* 157 */ 		jQuery('.wpb_toggle_content').each(function(index) {
/* 158 */ 			if ( jQuery(this).next().is('h4.wpb_toggle') == false ) {
/* 159 */ 				jQuery('<div class="last_toggle_el_margin"></div>').insertAfter(this);
/* 160 */ 			}
/* 161 */ 		});
/* 162 */ 	}
/* 163 */ }
/* 164 */ 
/* 165 */ /* Tabs + Tours
/* 166 *| ---------------------------------------------------------- */
/* 167 */ if ( typeof window['vc_tabsBehaviour'] !== 'function' ) {
/* 168 */ 	function vc_tabsBehaviour($tab) {
/* 169 */             jQuery(function($){$(document.body).off('click.preview', 'a')});
/* 170 */             var $call = $tab || jQuery('.wpb_tabs, .wpb_tour'),
/* 171 */                 ver = jQuery.ui ? jQuery.ui.version.split('.') : '1.10',
/* 172 */                 old_version = parseInt(ver[0])==1 &&  parseInt(ver[1]) < 9;
/* 173 */             // if($call.hasClass('ui-widget')) $call.tabs('destroy');
/* 174 */             $call.each(function(index) {
/* 175 */                 var $tabs,
/* 176 */                     interval = jQuery(this).attr("data-interval"),
/* 177 */                     tabs_array = [];
/* 178 */                 //
/* 179 */                 $tabs = jQuery(this).find('.wpb_tour_tabs_wrapper').tabs({
/* 180 */                     show: function(event, ui) {wpb_prepare_tab_content(event, ui);},
/* 181 */                     activate: function(event, ui) {wpb_prepare_tab_content(event, ui);}
/* 182 */                     }).tabs('rotate', interval*1000);
/* 183 */ 
/* 184 */                 jQuery(this).find('.wpb_tab').each(function(){ tabs_array.push(this.id); });
/* 185 */ 
/* 186 */                 jQuery(this).find('.wpb_tabs_nav a').click(function(e) {
/* 187 */                     e.preventDefault();
/* 188 */                     if ( jQuery.inArray( jQuery(this).attr('href'), tabs_array) ) {
/* 189 */                         if(old_version) {
/* 190 */                           $tabs.tabs("select", jQuery(this).attr('href'));
/* 191 */                         } else {
/* 192 */                           $tabs.tabs("option", "active", jQuery(jQuery(this).attr('href')).index()-1);
/* 193 */                         }
/* 194 */                         return false;
/* 195 */                     }
/* 196 */                 });
/* 197 */ 
/* 198 */                 jQuery(this).find('.wpb_prev_slide a, .wpb_next_slide a').click(function(e) {
/* 199 */                     e.preventDefault();
/* 200 */                     if(old_version) {

/* js_composer_front.js */

/* 201 */                         var index = $tabs.tabs('option', 'selected');
/* 202 */                         if ( jQuery(this).parent().hasClass('wpb_next_slide') ) { index++; }
/* 203 */                         else { index--; }
/* 204 */                         if ( index < 0 ) { index = $tabs.tabs("length") - 1; }
/* 205 */                         else if ( index >= $tabs.tabs("length") ) { index = 0; }
/* 206 */                         $tabs.tabs("select", index);
/* 207 */                     } else {
/* 208 */                         var index = $tabs.tabs( "option", "active"),
/* 209 */                             length = $tabs.find('.wpb_tab').length;
/* 210 */ 
/* 211 */                         if ( jQuery(this).parent().hasClass('wpb_next_slide') ) {
/* 212 */                             index = (index+1) >=length ? 0 : index+1;
/* 213 */                         } else {
/* 214 */                             index = index-1 < 0 ? length -1 : index-1;
/* 215 */                         }
/* 216 */ 
/* 217 */                         $tabs.tabs( "option", "active", index );
/* 218 */                     }
/* 219 */ 
/* 220 */                 });
/* 221 */ 
/* 222 */             });
/* 223 */ 	}
/* 224 */ }
/* 225 */ 
/* 226 */ /* Tabs + Tours
/* 227 *| ---------------------------------------------------------- */
/* 228 */ if ( typeof window['vc_accordionBehaviour'] !== 'function' ) {
/* 229 */ 	function vc_accordionBehaviour() {
/* 230 */ 		jQuery('.wpb_accordion').each(function(index) {
/* 231 */ 			var $tabs,
/* 232 */ 				interval = jQuery(this).attr("data-interval"),
/* 233 */                 active_tab = !isNaN(jQuery(this).data('active-tab')) && parseInt(jQuery(this).data('active-tab')) >  0 ? parseInt(jQuery(this).data('active-tab'))-1 : false,
/* 234 */                 collapsible =  active_tab === false || jQuery(this).data('collapsible') === 'yes';
/* 235 */ 			//
/* 236 */ 			$tabs = jQuery(this).find('.wpb_accordion_wrapper').accordion({
/* 237 */ 				header: "> div > h3",
/* 238 */ 				autoHeight: false,
/* 239 */                 heightStyle: "content",
/* 240 */                 active: active_tab,
/* 241 */                 collapsible: collapsible,
/* 242 */                 navigation: true,
/* 243 */                 change: function(event, ui){
/* 244 */                     if(jQuery.fn.isotope!=undefined) {
/* 245 */                         ui.newContent.find('.isotope').isotope("reLayout");
/* 246 */                     }
/* 247 */                     vc_carouselBehaviour();
/* 248 */                 }
/* 249 */ 			});
/* 250 */ 			//.tabs().tabs('rotate', interval*1000, true);

/* js_composer_front.js */

/* 251 */ 		});
/* 252 */ 	}
/* 253 */ }
/* 254 */ 
/* 255 */ /* Teaser grid: isotope
/* 256 *| ---------------------------------------------------------- */
/* 257 */ if ( typeof window['vc_teaserGrid'] !== 'function' ) {
/* 258 */ 	function vc_teaserGrid() {
/* 259 */         var layout_modes = {
/* 260 */             fitrows: 'fitRows',
/* 261 */             masonry: 'masonry'
/* 262 */         }
/* 263 */         jQuery('.wpb_grid .teaser_grid_container:not(.wpb_carousel), .wpb_filtered_grid .teaser_grid_container:not(.wpb_carousel)').each(function(){
/* 264 */             var $container = jQuery(this);
/* 265 */             var $thumbs = $container.find('.wpb_thumbnails');
/* 266 */             var layout_mode = $thumbs.attr('data-layout-mode');
/* 267 */             $thumbs.isotope({
/* 268 */                 // options
/* 269 */                 itemSelector : '.isotope-item',
/* 270 */                 layoutMode : (layout_modes[layout_mode]==undefined ? 'fitRows' : layout_modes[layout_mode])
/* 271 */             });
/* 272 */             $container.find('.categories_filter a').data('isotope', $thumbs).click(function(e){
/* 273 */                 e.preventDefault();
/* 274 */                 var $thumbs = jQuery(this).data('isotope');
/* 275 */                 jQuery(this).parent().parent().find('.active').removeClass('active');
/* 276 */                 jQuery(this).parent().addClass('active');
/* 277 */                 $thumbs.isotope({filter: jQuery(this).attr('data-filter')});
/* 278 */             });
/* 279 */             jQuery(window).bind('load resize', function() {
/* 280 */                 $thumbs.isotope("reLayout");
/* 281 */             });
/* 282 */         });
/* 283 */ 
/* 284 */         /*
/* 285 *| 		var isotope = jQuery('.wpb_grid ul.thumbnails');
/* 286 *| 		if ( isotope.length > 0 ) {
/* 287 *| 			isotope.isotope({
/* 288 *| 				// options
/* 289 *| 				itemSelector : '.isotope-item',
/* 290 *| 				layoutMode : 'fitRows'
/* 291 *| 			});
/* 292 *| 			jQuery(window).load(function() {
/* 293 *| 				isotope.isotope("reLayout");
/* 294 *| 			});
/* 295 *| 		}
/* 296 *| 		*/
/* 297 */ 	}
/* 298 */ }
/* 299 */ 
/* 300 */ if ( typeof window['vc_carouselBehaviour'] !== 'function' ) {

/* js_composer_front.js */

/* 301 */   function vc_carouselBehaviour() {
/* 302 */     jQuery(".wpb_carousel").each(function() {
/* 303 */             var $this = jQuery(this);
/* 304 */             if($this.data('carousel_enabled') !== true && $this.is(':visible')) {
/* 305 */                 $this.data('carousel_enabled', true);
/* 306 */                 var carousel_width = jQuery(this).width(),
/* 307 */                     visible_count = getColumnsCount(jQuery(this)),
/* 308 */                     carousel_speed = 500;
/* 309 */                 if ( jQuery(this).hasClass('columns_count_1') ) {
/* 310 */                     carousel_speed = 900;
/* 311 */                 }
/* 312 */                 /* Get margin-left value from the css grid and apply it to the carousele li items (margin-right), before carousele initialization */
/* 313 */                 var carousele_li = jQuery(this).find('.wpb_thumbnails-fluid li');
/* 314 */                 carousele_li.css({"margin-right": carousele_li.css("margin-left"), "margin-left" : 0 });
/* 315 */ 
/* 316 */                 jQuery(this).find('.wpb_wrapper:eq(0)').jCarouselLite({
/* 317 */                     btnNext: jQuery(this).find('.next'),
/* 318 */                     btnPrev: jQuery(this).find('.prev'),
/* 319 */                     visible: visible_count,
/* 320 */                     speed: carousel_speed
/* 321 */                 })
/* 322 */                     .width('100%');//carousel_width
/* 323 */ 
/* 324 */                 var fluid_ul = jQuery(this).find('ul.wpb_thumbnails-fluid');
/* 325 */                 fluid_ul.width(fluid_ul.width()+300);
/* 326 */ 
/* 327 */                 jQuery(window).resize(function() {
/* 328 */                     var before_resize = screen_size;
/* 329 */                     screen_size = getSizeName();
/* 330 */                     if ( before_resize != screen_size ) {
/* 331 */                         window.setTimeout('location.reload()', 20);
/* 332 */                     }
/* 333 */                 });
/* 334 */             }
/* 335 */ 
/* 336 */     });
/* 337 */         /*
/* 338 *|         if(jQuery.fn.bxSlider !== undefined ) {
/* 339 *|             jQuery('.bxslider').each(function(){
/* 340 *|                var $slider = jQuery(this);
/* 341 *|                $slider.bxSlider($slider.data('settings'));
/* 342 *|             });
/* 343 *|         }
/* 344 *|         */
/* 345 */         if(window.Swiper !== undefined) {
/* 346 */ 
/* 347 */             jQuery('.swiper-container').each(function(){
/* 348 */                 var $this = jQuery(this),
/* 349 */                     my_swiper,
/* 350 */                     max_slide_size = 0,

/* js_composer_front.js */

/* 351 */                     options = jQuery(this).data('settings');
/* 352 */ 
/* 353 */                     if(options.mode === 'vertical') {
/* 354 */                         $this.find('.swiper-slide').each(function(){
/* 355 */                             var height = jQuery(this).outerHeight(true);
/* 356 */                             if(height > max_slide_size) max_slide_size = height;
/* 357 */                         });
/* 358 */                         $this.height(max_slide_size);
/* 359 */                         $this.css('overflow', 'hidden');
/* 360 */                     }
/* 361 */                     jQuery(window).resize(function(){
/* 362 */                         $this.find('.swiper-slide').each(function(){
/* 363 */                             var height = jQuery(this).outerHeight(true);
/* 364 */                             if(height > max_slide_size) max_slide_size = height;
/* 365 */                         });
/* 366 */                         $this.height(max_slide_size);
/* 367 */                     });
/* 368 */                     my_swiper = jQuery(this).swiper(jQuery.extend(options, {
/* 369 */                     onFirstInit: function(swiper) {
/* 370 */                         if(swiper.slides.length < 2) {
/* 371 */                             $this.find('.vc-arrow-left,.vc-arrow-right').hide();
/* 372 */                         } else if(swiper.activeIndex === 0  && swiper.params.loop !== true) {
/* 373 */                             $this.find('.vc-arrow-left').hide();
/* 374 */                         } else {
/* 375 */                             $this.find('.vc-arrow-left').show();
/* 376 */                         }
/* 377 */                     },
/* 378 */                     onSlideChangeStart: function(swiper) {
/* 379 */                         if(swiper.slides.length > 1 && swiper.params.loop !== true) {
/* 380 */                             if(swiper.activeIndex === 0) {
/* 381 */                                 $this.find('.vc-arrow-left').hide();
/* 382 */                             } else {
/* 383 */                                 $this.find('.vc-arrow-left').show();
/* 384 */                             }
/* 385 */                             if(swiper.slides.length-1 === swiper.activeIndex) {
/* 386 */                                 $this.find('.vc-arrow-right').hide();
/* 387 */                             } else {
/* 388 */                                 $this.find('.vc-arrow-right').show();
/* 389 */                             }
/* 390 */                         }
/* 391 */                     }
/* 392 */                 }));
/* 393 */                 $this.find('.vc-arrow-left').click(function(e){
/* 394 */                     e.preventDefault();
/* 395 */                     my_swiper.swipePrev();
/* 396 */                 });
/* 397 */                 $this.find('.vc-arrow-right').click(function(e){
/* 398 */                     e.preventDefault();
/* 399 */                     my_swiper.swipeNext();
/* 400 */                 });

/* js_composer_front.js */

/* 401 */                 my_swiper.reInit();
/* 402 */             });
/* 403 */ 
/* 404 */         }
/* 405 */ 
/* 406 */ 	}
/* 407 */ }
/* 408 */ 
/* 409 */ if ( typeof window['vc_slidersBehaviour'] !== 'function' ) {
/* 410 */ 	function vc_slidersBehaviour() {
/* 411 */ 		//var sliders_count = 0;
/* 412 */ 		jQuery('.wpb_gallery_slides').each(function(index) {
/* 413 */ 			var this_element = jQuery(this);
/* 414 */ 			var ss_count = 0;
/* 415 */ 
/* 416 */ 			/*if ( this_element.hasClass('wpb_slider_fading') ) {
/* 417 *| 				var sliderSpeed = 500, sliderTimeout = this_element.attr('data-interval')*1000, slider_fx = 'fade';
/* 418 *| 				var current_ss;
/* 419 *| 
/* 420 *| 				function slideshowOnBefore(currSlideElement, nextSlideElement, options) {
/* 421 *| 					jQuery(nextSlideElement).css({"position" : "absolute" });
/* 422 *| 					jQuery(nextSlideElement).find("div.description").animate({"opacity": 0}, 0);
/* 423 *| 				}
/* 424 *| 
/* 425 *| 				function slideshowOnAfter(currSlideElement, nextSlideElement, options) {
/* 426 *| 					jQuery(nextSlideElement).find("div.description").animate({"opacity": 1}, 2000);
/* 427 *| 
/* 428 *| 					jQuery(nextSlideElement).css({"position" : "static" });
/* 429 *| 					var new_h = jQuery(nextSlideElement).find('img').height();
/* 430 *| 					if ( jQuery.isNumeric(new_h) ) {
/* 431 *| 						//this_element.animate({ "height" : new_h }, sliderSpeed );
/* 432 *| 					}
/* 433 *| 				}
/* 434 *| 
/* 435 *| 				this_element.find('ul')
/* 436 *| 				.before('<div class="ss_nav ss_nav_'+ss_count+'"></div><div class="wpb_fading_nav"><a id="next_'+ss_count+'" href="#next"></a> <a id="prev_'+ss_count+'" href="#prev"></a></div>')
/* 437 *| 				.cycle({
/* 438 *| 					fx: slider_fx, // choose your transition type, ex: fade, scrollUp, shuffle, etc...
/* 439 *| 					pause: 1,
/* 440 *| 					speed: sliderSpeed,
/* 441 *| 					timeout: sliderTimeout,
/* 442 *| 					delay: -ss_count * 1000,
/* 443 *| 					before: slideshowOnBefore,
/* 444 *| 					after:slideshowOnAfter,
/* 445 *| 					pager:  '.ss_nav_'+ss_count
/* 446 *| 				});
/* 447 *| 				//.find('.description').width(jQuery(this).width() - 20);
/* 448 *| 				ss_count++;
/* 449 *| 			}
/* 450 *| 			else*/

/* js_composer_front.js */

/* 451 */ 			if ( this_element.hasClass('wpb_slider_nivo') ) {
/* 452 */ 				var sliderSpeed = 800,
/* 453 */ 					sliderTimeout = this_element.attr('data-interval')*1000;
/* 454 */ 
/* 455 */ 				if ( sliderTimeout == 0 ) sliderTimeout = 9999999999;
/* 456 */ 
/* 457 */ 				this_element.find('.nivoSlider').nivoSlider({
/* 458 */ 					effect: 'boxRainGrow,boxRain,boxRainReverse,boxRainGrowReverse', // Specify sets like: 'fold,fade,sliceDown'
/* 459 */ 					slices: 15, // For slice animations
/* 460 */ 					boxCols: 8, // For box animations
/* 461 */ 					boxRows: 4, // For box animations
/* 462 */ 					animSpeed: sliderSpeed, // Slide transition speed
/* 463 */ 					pauseTime: sliderTimeout, // How long each slide will show
/* 464 */ 					startSlide: 0, // Set starting Slide (0 index)
/* 465 */ 					directionNav: true, // Next & Prev navigation
/* 466 */ 					directionNavHide: true, // Only show on hover
/* 467 */ 					controlNav: true, // 1,2,3... navigation
/* 468 */ 					keyboardNav: false, // Use left & right arrows
/* 469 */ 					pauseOnHover: true, // Stop animation while hovering
/* 470 */ 					manualAdvance: false, // Force manual transitions
/* 471 */ 					prevText: 'Prev', // Prev directionNav text
/* 472 */ 					nextText: 'Next' // Next directionNav text
/* 473 */ 				});
/* 474 */ 			}
/* 475 */ 			else if ( this_element.hasClass('wpb_flexslider') && 1==2) { /* TODO: remove this */
/* 476 */                 /*
/* 477 *| 				var sliderSpeed = 800,
/* 478 *| 					sliderTimeout = this_element.attr('data-interval')*1000,
/* 479 *| 					sliderFx = this_element.attr('data-flex_fx'),
/* 480 *| 					slideshow = true;
/* 481 *| 				if ( sliderTimeout == 0 ) slideshow = false;
/* 482 *| 
/* 483 *| 				this_element.flexslider({
/* 484 *| 					animation: sliderFx,
/* 485 *| 					slideshow: slideshow,
/* 486 *| 					slideshowSpeed: sliderTimeout,
/* 487 *| 					sliderSpeed: sliderSpeed,
/* 488 *| 					smoothHeight: true
/* 489 *| 
/* 490 *| 				});
/* 491 *|                 */
/* 492 */ 
/* 493 */                 /*
/* 494 *|                 var $first_object = this_element.find('li:first').show().find('*:not(a)');
/* 495 *| 
/* 496 *|                 $first_object.bind('load', function() {
/* 497 *|                     if(!this_element.find('.flex-control-nav').is('ol')) {
/* 498 *|                         this_element.flexslider({
/* 499 *|                             animation: sliderFx,
/* 500 *|                             slideshow: slideshow,

/* js_composer_front.js */

/* 501 *|                             slideshowSpeed: sliderTimeout,
/* 502 *|                             sliderSpeed: sliderSpeed,
/* 503 *|                             smoothHeight: true
/* 504 *|                         });
/* 505 *|                     }
/* 506 *|                 });
/* 507 *| 
/* 508 *|                 window.setTimeout(function(){
/* 509 *|                     if(!this_element.find('.flex-control-nav').is('ol')) {
/* 510 *|                         this_element.flexslider({
/* 511 *|                             animation: sliderFx,
/* 512 *|                             slideshow: slideshow,
/* 513 *|                             slideshowSpeed: sliderTimeout,
/* 514 *|                             sliderSpeed: sliderSpeed,
/* 515 *|                             smoothHeight: true
/* 516 *|                         });
/* 517 *|                     }
/* 518 *|                 }, 5000);
/* 519 *|                 */
/* 520 */ 			}
/* 521 */ 			else if ( this_element.hasClass('wpb_image_grid') ) {
/* 522 */ 				var isotope = this_element.find('.wpb_image_grid_ul');
/* 523 */ 				isotope.isotope({
/* 524 */ 					// options
/* 525 */ 					itemSelector : '.isotope-item',
/* 526 */ 					layoutMode : 'fitRows'
/* 527 */ 				});
/* 528 */ 				jQuery(window).load(function() {
/* 529 */ 					isotope.isotope("reLayout");
/* 530 */ 				});
/* 531 */ 			}
/* 532 */ 		});
/* 533 */ 	}
/* 534 */ }
/* 535 */ 
/* 536 */ if ( typeof window['vc_prettyPhoto'] !== 'function' ) {
/* 537 */ 	function vc_prettyPhoto() {
/* 538 */ 		try {
/* 539 */ 			// just in case. maybe prettyphoto isnt loaded on this site
/* 540 */ 			jQuery('a.prettyphoto, .gallery-icon a[href*=".jpg"]').prettyPhoto({
/* 541 */ 				animationSpeed: 'normal', /* fast/slow/normal */
/* 542 */ 				padding: 15, /* padding for each side of the picture */
/* 543 */ 				opacity: 0.7, /* Value betwee 0 and 1 */
/* 544 */ 				showTitle: true, /* true/false */
/* 545 */ 				allowresize: true, /* true/false */
/* 546 */ 				counter_separator_label: '/', /* The separator for the gallery counter 1 "of" 2 */
/* 547 */ 				//theme: 'light_square', /* light_rounded / dark_rounded / light_square / dark_square */
/* 548 */ 				hideflash: false, /* Hides all the flash object on a page, set to TRUE if flash appears over prettyPhoto */
/* 549 */                 deeplinking: false, /* Allow prettyPhoto to update the url to enable deeplinking. */
/* 550 */ 				modal: false, /* If set to true, only the close button will close the window */

/* js_composer_front.js */

/* 551 */ 				callback: function() {
/* 552 */ 					var url = location.href;
/* 553 */ 					var hashtag = (url.indexOf('#!prettyPhoto')) ? true : false;
/* 554 */ 					if (hashtag) location.hash = "!";
/* 555 */ 				} /* Called when prettyPhoto is closed */,
/* 556 */ 				social_tools : ''
/* 557 */ 			});
/* 558 */ 		} catch (err) { }
/* 559 */ 	}
/* 560 */ }
/* 561 */ /* Helper
/* 562 *| ---------------------------------------------------------- */
/* 563 */ function getColumnsCount(el) {
/* 564 */ 	var find = false,
/* 565 */ 		i = 1;
/* 566 */ 
/* 567 */ 	while ( find == false ) {
/* 568 */ 		if ( el.hasClass('columns_count_'+i) ) {
/* 569 */ 			find = true;
/* 570 */ 			return i;
/* 571 */ 		}
/* 572 */ 		i++;
/* 573 */ 	}
/* 574 */ }
/* 575 */ 
/* 576 */ var screen_size = getSizeName();
/* 577 */ function getSizeName() {
/* 578 */ 	var screen_size = '',
/* 579 */ 		screen_w = jQuery(window).width();
/* 580 */ 
/* 581 */ 	if ( screen_w > 1170 ) {
/* 582 */ 		screen_size = "desktop_wide";
/* 583 */ 	}
/* 584 */ 	else if ( screen_w > 960 && screen_w < 1169 ) {
/* 585 */ 		screen_size = "desktop";
/* 586 */ 	}
/* 587 */ 	else if ( screen_w > 768 && screen_w < 959 ) {
/* 588 */ 		screen_size = "tablet";
/* 589 */ 	}
/* 590 */ 	else if ( screen_w > 300 && screen_w < 767 ) {
/* 591 */ 		screen_size = "mobile";
/* 592 */ 	}
/* 593 */ 	else if ( screen_w < 300 ) {
/* 594 */ 		screen_size = "mobile_portrait";
/* 595 */ 	}
/* 596 */ 	return screen_size;
/* 597 */ }
/* 598 */ 
/* 599 */ 
/* 600 */ function loadScript(url, $obj, callback){

/* js_composer_front.js */

/* 601 */ 
/* 602 */     var script = document.createElement("script")
/* 603 */     script.type = "text/javascript";
/* 604 */ 
/* 605 */     if (script.readyState){  //IE
/* 606 */         script.onreadystatechange = function(){
/* 607 */             if (script.readyState == "loaded" ||
/* 608 */                 script.readyState == "complete"){
/* 609 */                 script.onreadystatechange = null;
/* 610 */                 callback();
/* 611 */             }
/* 612 */         };
/* 613 */     } else {  //Others
/* 614 */         /*
/* 615 *|         script.onload = function(){
/* 616 *| 
/* 617 *|             callback();
/* 618 *|         };
/* 619 *|          */
/* 620 */     }
/* 621 */ 
/* 622 */     script.src = url;
/* 623 */     $obj.get(0).appendChild(script);
/* 624 */ }
/* 625 */ 
/* 626 */ /**
/* 627 *|  * Prepare html to correctly display inside tab container
/* 628 *|  *
/* 629 *|  * @param event - ui tab event 'show'
/* 630 *|  * @param ui - jquery ui tabs object
/* 631 *|  */
/* 632 */ 
/* 633 */ function wpb_prepare_tab_content(event, ui) {
/* 634 */     var panel = ui.panel || ui.newPanel;
/* 635 */     vc_carouselBehaviour();
/* 636 */     var $ui_panel = jQuery(panel).find('.isotope'),
/* 637 */         $google_maps = jQuery(panel).find('.wpb_gmaps_widget');
/* 638 */     if ($ui_panel.length > 0) {
/* 639 */ 	    $ui_panel.isotope("reLayout");
/* 640 */     }
/* 641 */ 
/* 642 */     if($google_maps.length && !$google_maps.is('.map_ready')) {
/* 643 */         var $frame = $google_maps.find('iframe');
/* 644 */         $frame.attr('src', $frame.attr('src'));
/* 645 */         $google_maps.addClass('map_ready');
/* 646 */     }
/* 647 */ }
/* 648 */ 
/* 649 */ 

;
/* transition.js */

/* 1  */ /* ========================================================================
/* 2  *|  * Bootstrap: transition.js v3.0.0
/* 3  *|  * http://twbs.github.com/bootstrap/javascript.html#transitions
/* 4  *|  * ========================================================================
/* 5  *|  * Copyright 2013 Twitter, Inc.
/* 6  *|  *
/* 7  *|  * Licensed under the Apache License, Version 2.0 (the "License");
/* 8  *|  * you may not use this file except in compliance with the License.
/* 9  *|  * You may obtain a copy of the License at
/* 10 *|  *
/* 11 *|  * http://www.apache.org/licenses/LICENSE-2.0
/* 12 *|  *
/* 13 *|  * Unless required by applicable law or agreed to in writing, software
/* 14 *|  * distributed under the License is distributed on an "AS IS" BASIS,
/* 15 *|  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/* 16 *|  * See the License for the specific language governing permissions and
/* 17 *|  * limitations under the License.
/* 18 *|  * ======================================================================== */
/* 19 */ 
/* 20 */ 
/* 21 */ +function ($) { "use strict";
/* 22 */ 
/* 23 */   // CSS TRANSITION SUPPORT (Shoutout: http://www.modernizr.com/)
/* 24 */   // ============================================================
/* 25 */ 
/* 26 */   function transitionEnd() {
/* 27 */     var el = document.createElement('bootstrap')
/* 28 */ 
/* 29 */     var transEndEventNames = {
/* 30 */       'WebkitTransition' : 'webkitTransitionEnd'
/* 31 */     , 'MozTransition'    : 'transitionend'
/* 32 */     , 'OTransition'      : 'oTransitionEnd otransitionend'
/* 33 */     , 'transition'       : 'transitionend'
/* 34 */     }
/* 35 */ 
/* 36 */     for (var name in transEndEventNames) {
/* 37 */       if (el.style[name] !== undefined) {
/* 38 */         return { end: transEndEventNames[name] }
/* 39 */       }
/* 40 */     }
/* 41 */   }
/* 42 */ 
/* 43 */   // http://blog.alexmaccaw.com/css-transitions
/* 44 */   $.fn.emulateTransitionEnd = function (duration) {
/* 45 */     var called = false, $el = this
/* 46 */     $(this).one($.support.transition.end, function () { called = true })
/* 47 */     var callback = function () { if (!called) $($el).trigger($.support.transition.end) }
/* 48 */     setTimeout(callback, duration)
/* 49 */     return this
/* 50 */   }

/* transition.js */

/* 51 */ 
/* 52 */   $(function () {
/* 53 */     $.support.transition = transitionEnd()
/* 54 */   })
/* 55 */ 
/* 56 */ }(window.jQuery);
/* 57 */ 

;
/* vc_carousel.js */

/* 1   */ /* ========================================================================
/* 2   *|  * VC: carousel.js v0.4.5
/* 3   *|  * Fork Bootstrap: carousel.js v3.0.0
/* 4   *|  * http://twbs.github.com/bootstrap/javascript.html#carousel
/* 5   *|  * ========================================================================
/* 6   *|  * Copyright 2012 Twitter, Inc.
/* 7   *|  *
/* 8   *|  * Licensed under the Apache License, Version 2.0 (the "License");
/* 9   *|  * you may not use this file except in compliance with the License.
/* 10  *|  * You may obtain a copy of the License at
/* 11  *|  *
/* 12  *|  * http://www.apache.org/licenses/LICENSE-2.0
/* 13  *|  *
/* 14  *|  * Unless required by applicable law or agreed to in writing, software
/* 15  *|  * distributed under the License is distributed on an "AS IS" BASIS,
/* 16  *|  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/* 17  *|  * See the License for the specific language governing permissions and
/* 18  *|  * limitations under the License.
/* 19  *|  * ======================================================================== */
/* 20  */ 
/* 21  */ 
/* 22  */ ;(function($) { "use strict";
/* 23  */ 
/* 24  */   // CAROUSEL CLASS DEFINITION
/* 25  */   // =========================
/* 26  */ 
/* 27  */   var Carousel = function (element, options) {
/* 28  */     this.$element    = $(element)
/* 29  */     this.$indicators = this.$element.find('.vc-carousel-indicators')
/* 30  */     this.options     = options
/* 31  */     this.paused      =
/* 32  */     this.sliding     =
/* 33  */     this.interval    =
/* 34  */     this.$active     =
/* 35  */     this.$items      = null
/* 36  */     this.options.pause == 'hover' && this.$element
/* 37  */       .on('mouseenter', $.proxy(this.pause, this))
/* 38  */       .on('mouseleave', $.proxy(this.cycle, this))
/* 39  */     this._build() // new
/* 40  */   }
/* 41  */ 
/* 42  */   Carousel.DEFAULTS = {
/* 43  */     mode: 'horizontal'
/* 44  */   , partial: false
/* 45  */   , interval: 5000
/* 46  */   , pause: 'hover'
/* 47  */   , wrap: false
/* 48  */   , autoHeight: false
/* 49  */   , perView: 1
/* 50  */   , hideOnEnd: false

/* vc_carousel.js */

/* 51  */   }
/* 52  */   Carousel.prototype.cycle =  function (e) {
/* 53  */     e || (this.paused = false)
/* 54  */ 
/* 55  */     this.interval && clearInterval(this.interval)
/* 56  */ 
/* 57  */     this.options.interval
/* 58  */       && !this.paused
/* 59  */       && (this.interval = setInterval($.proxy(this.next, this), this.options.interval))
/* 60  */       this.touch_start_position = 0;
/* 61  */     return this
/* 62  */   }
/* 63  */   Carousel.prototype.getActiveIndex = function () {
/* 64  */     this.$active = this.$element.find('.vc-item.vc-active')
/* 65  */     if(!this.$active.length) this.$active = this.$element.find('.vc-item:first').addClass('vc-active')
/* 66  */     this.$items  = this.$active.parent().children()
/* 67  */     return this.$items.index(this.$active)
/* 68  */   }
/* 69  */   Carousel.prototype.showHideControl = function(index) {
/* 70  */     if(typeof index === 'undefined') var index = this.getActiveIndex()
/* 71  */     this.$left_control[index===0 ? 'hide' : 'show']()
/* 72  */     this.$right_control[index===this.items_count-1 ? 'hide' : 'show']()
/* 73  */   }
/* 74  */   Carousel.prototype.to = function (pos) {
/* 75  */     var that        = this
/* 76  */     var activeIndex = this.getActiveIndex()
/* 77  */ 
/* 78  */     if (pos > (this.$items.length - 1) || pos < 0) return
/* 79  */ 
/* 80  */     if (this.sliding)       return this.$element.one('slid', function () { that.to(pos) })
/* 81  */     if (activeIndex == pos) return this.pause().cycle()
/* 82  */ 
/* 83  */     return this.slide(pos > activeIndex ? 'next' : 'prev', $(this.$items[pos]))
/* 84  */   }
/* 85  */ 
/* 86  */   Carousel.prototype.pause = function (e) {
/* 87  */     e || (this.paused = true)
/* 88  */ 
/* 89  */     if (this.$element.find('.vc-next, .vc-prev').length && $.support.transition.end) {
/* 90  */       this.$element.trigger($.support.transition.end)
/* 91  */       this.cycle(true)
/* 92  */     }
/* 93  */ 
/* 94  */     this.interval = clearInterval(this.interval)
/* 95  */ 
/* 96  */     return this
/* 97  */   }
/* 98  */ 
/* 99  */   Carousel.prototype.next = function () {
/* 100 */     if (this.sliding) return

/* vc_carousel.js */

/* 101 */     return this.slide('next')
/* 102 */   }
/* 103 */ 
/* 104 */   Carousel.prototype.prev = function () {
/* 105 */     if (this.sliding) return
/* 106 */     return this.slide('prev')
/* 107 */   }
/* 108 */ 
/* 109 */   Carousel.prototype.slide = function (type, next) {
/* 110 */     var $active   = this.$element.find('.vc-item.vc-active')
/* 111 */     var $next     = next || $active[type]()
/* 112 */     var isCycling = this.interval
/* 113 */     var direction = type == 'next' ? 'vc-left' : 'vc-right'
/* 114 */     var fallback  = type == 'next' ? 'first' : 'last'
/* 115 */     var that      = this
/* 116 */     if (!$next.length) {
/* 117 */       if (!this.options.wrap) {
/* 118 */         this.returnSwipedSlide()
/* 119 */         return
/* 120 */       }
/* 121 */       $next = this.$element.find('.vc-item')[fallback]()
/* 122 */     }
/* 123 */ 
/* 124 */     this.sliding = true
/* 125 */ 
/* 126 */     isCycling && this.pause()
/* 127 */ 
/* 128 */     var e = $.Event('slide.vc.carousel', { relatedTarget: $next[0], direction: direction })
/* 129 */ 
/* 130 */     if ($next.hasClass('vc-active')) return
/* 131 */ 
/* 132 */     if (this.$indicators.length) {
/* 133 */       this.$indicators.find('.vc-active').removeClass('vc-active')
/* 134 */       this.$indicators.find('.vc-partial').removeClass('vc-partial')
/* 135 */       this.$element.one('slid', function () {
/* 136 */         var index = that.getActiveIndex(),
/* 137 */             $nextIndicator = $(that.$indicators.children().slice(index, that.getActiveIndex() + that.options.perView))
/* 138 */         $nextIndicator && $nextIndicator.addClass('vc-active')
/* 139 */         that.options.partial && $nextIndicator && (index+1 < that.items_count ? $nextIndicator.last().next().addClass('vc-partial') : $nextIndicator.first().prev().addClass('vc-partial'))
/* 140 */         if(that.options.hideOnEnd) that.showHideControl(index)
/* 141 */       })
/* 142 */     }
/* 143 */     this.current_index = $next.index()
/* 144 */     if(this.current_index > this.items_count) {
/* 145 */       this.current_index = 0
/* 146 */     } else if(this.current_index < 0) {
/* 147 */       this.current_index = this.items_count -1
/* 148 */     }
/* 149 */     if(this.options.autoHeight) {
/* 150 */       this.current_pos_value = -1 * this._step * this.current_index

/* vc_carousel.js */

/* 151 */     } else {
/* 152 */       this.current_pos_value = -1 * $next.position()[this.animation_position]
/* 153 */     }
/* 154 */     if(this.options.partial && this.current_index >= this.items_count-1) {
/* 155 */       this.current_pos_value += this._step*(1-this.partial_part)
/* 156 */     }
/* 157 */     if ($.support.transition && this.$element.hasClass('vc-slide')) {
/* 158 */       this.$element.trigger(e)
/* 159 */       if (e.isDefaultPrevented()) return
/* 160 */       this.$slideline_inner
/* 161 */         .addClass('vc-transition')
/* 162 */         .css(this.animation_position,  this.current_pos_value + that.pos_units)
/* 163 */       if(!this.options.autoHeight) this.recalculateSlidelineHeight($next.height(), true)
/* 164 */       this.$slideline_inner.one($.support.transition.end, function(){
/* 165 */         $next.addClass('vc-active')
/* 166 */         $active.removeClass('vc-active')
/* 167 */         that.$slideline_inner.removeClass([type, 'vc-transition'].join(' '))
/* 168 */         that.sliding = false
/* 169 */         that.removeSwipeAnimationSpeed()
/* 170 */         setTimeout(function () { that.$element.trigger('slid') }, 0)
/* 171 */       }).emulateTransitionEnd(this.transition_speed)
/* 172 */     } else {
/* 173 */       this.$element.trigger(e)
/* 174 */       if (e.isDefaultPrevented()) return
/* 175 */       $active.removeClass('vc-active')
/* 176 */       $next.addClass('vc-active')
/* 177 */       this.sliding = false
/* 178 */       this.$slideline_inner.css(this.animation_position, this.current_pos_value + that.pos_units)
/* 179 */     }
/* 180 */     isCycling && this.cycle()
/* 181 */     return this
/* 182 */   }
/* 183 */   Carousel.prototype.setSwipeAnimationSpeed = function() {
/* 184 */     this.$slideline_inner.addClass('vc-swipe-transition')
/* 185 */   }
/* 186 */   Carousel.prototype.removeSwipeAnimationSpeed = function() {
/* 187 */     this.$slideline_inner.removeClass('vc-swipe-transition')
/* 188 */ 
/* 189 */   }
/* 190 */     /**
/* 191 *|      * Velocity
/* 192 *|      * @param   {Number}    delta_time
/* 193 *|      * @param   {Number}    delta_x
/* 194 *|      * @param   {Number}    delta_y
/* 195 *|      * @returns {Object}    velocity
/* 196 *|      */
/* 197 */     Carousel.prototype.velocity =  function(time, x) {
/* 198 */       return {
/* 199 */           x: Math.abs(x / time) || 0
/* 200 */       }

/* vc_carousel.js */

/* 201 */     }
/* 202 */     Carousel.prototype.recalculateSlidelineHeight = function(height, animate) {
/* 203 */       if(animate === true) {
/* 204 */         this.$slideline.animate({height: height})
/* 205 */       } else {
/* 206 */         this.$slideline.height(height)
/* 207 */       }
/* 208 */     }
/* 209 */     /**
/* 210 *|      * Change layout size after resizing of window.
/* 211 *|      */
/* 212 */     Carousel.prototype.resizeAction = function() {
/* 213 */       var max_height = 0,
/* 214 */           new_slideline_height = 0
/* 215 */       if(this.options.mode === 'horizontal') {
/* 216 */         this.el_effect_size = this.$element.width() * ( this.options.partial ? this.partial_part : 1 )
/* 217 */         this.$slideline.width(this.items_count*this.el_effect_size)
/* 218 */       }
/* 219 */ 
/* 220 */       if (this.options.autoHeight) {
/* 221 */         this.$items.height('auto')
/* 222 */         this.$items.each(function(){
/* 223 */           var item_height = $(this).height()
/* 224 */           if(item_height > max_height) max_height = item_height
/* 225 */         })
/* 226 */         this.$items.height(max_height)
/* 227 */       } else {
/* 228 */         this.recalculateSlidelineHeight(this.$active.height())
/* 229 */       }
/* 230 */       if(this.options.mode === 'vertical') {
/* 231 */         this._step = this.$active.height()
/* 232 */         new_slideline_height = this.$active.height() * this.options.perView * (this.options.partial ? (1 + 1-this.partial_part) : 1)
/* 233 */         this.recalculateSlidelineHeight(new_slideline_height, false)
/* 234 */         this.$slideline_inner.css({top: -1 * this.$active.position().top})
/* 235 */         this.el_effect_size = this._step
/* 236 */       }
/* 237 */     }
/* 238 */     Carousel.prototype.returnSwipedSlide = function() {
/* 239 */       var params = {}
/* 240 */       params[this.animation_position] = this.current_pos_value + this.pos_units
/* 241 */       this.$slideline_inner.animate(params)
/* 242 */     }
/* 243 */     Carousel.prototype._build = function() {
/* 244 */       var el                      = this.$element.get(0),
/* 245 */           _touch_start_position   = false,
/* 246 */           _touch_start_time       = 0,
/* 247 */           _pos_before_touch      = 0,
/* 248 */           _diff                   = 0,
/* 249 */           _moved                  = false,
/* 250 */           that                    = this,

/* vc_carousel.js */

/* 251 */           mode                    = this.options.mode
/* 252 */       this.getActiveIndex()
/* 253 */ 
/* 254 */       this.el_width               = 0
/* 255 */       this.items_count            = this.$items.length
/* 256 */ 
/* 257 */       this.$slideline             = this.$element.find('.vc-carousel-slideline')
/* 258 */       this.slideline              = this.$slideline.get(0)
/* 259 */       this.$slideline_inner       = this.$slideline.find('> div')
/* 260 */       this.slideline_inner        = this.$slideline_inner.get(0)
/* 261 */ 
/* 262 */       this.partial_part           = 0.8
/* 263 */       this._slide_width           = 0
/* 264 */       this.swipe_velocity         = 0.7
/* 265 */       this.current_pos_value      = 0
/* 266 */       this.current_index          = 0 // TODO: default start position by options
/* 267 */       this.el_effect_size         = 0
/* 268 */       this.transition_speed       = 600
/* 269 */ 
/* 270 */       this.$left_control = this.$element.find('.vc-left.vc-carousel-control')
/* 271 */       this.$right_control = this.$element.find('.vc-right.vc-carousel-control')
/* 272 */ 
/* 273 */       // Enable autoHeight if partial
/* 274 */       if(this.options.partial) this.options.autoHeight = true
/* 275 */       // Add Css classes for perView > 1
/* 276 */       if(this.options.perView > 1) this.$element.addClass('vc-per-view-more vc-per-view-' + this.options.perView)
/* 277 */ 
/* 278 */       if( mode === 'horizontal') {
/* 279 */         this.pos_units = '%'
/* 280 */         this._step = 100.00/this.items_count/this.options.perView
/* 281 */         this.animation_position = 'left'
/* 282 */         this.$items.width(this._step + this.pos_units)
/* 283 */         this.touch_direction = 'pageX'
/* 284 */       } else {
/* 285 */         this.pos_units = 'px'
/* 286 */         this.animation_position = 'top'
/* 287 */         this.touch_direction = 'pageY'
/* 288 */       }
/* 289 */       // Hide first control if this.current_index === 0
/* 290 */       if(this.options.hideOnEnd) this.showHideControl()
/* 291 */       // Add partial css class if partial
/* 292 */       if(this.options.partial) this.$element.addClass('vc_partial')
/* 293 */       // Set indicator
/* 294 */       if(this.$indicators.length) {
/* 295 */         var $active_indecators = that.$indicators.children()
/* 296 */                                                  .slice(this.current_index, this.current_index + this.options.perView)
/* 297 */                                                  .addClass('vc-active')
/* 298 */         this.options.partial && $active_indecators.last().next().addClass('vc_partial')
/* 299 */       }
/* 300 */       $(window).resize(this.resizeAction.bind(this)); this.resizeAction()

/* vc_carousel.js */

/* 301 */ 
/* 302 */       el.addEventListener("touchstart", function(e){
/* 303 */         _touch_start_position = parseFloat(e[that.touch_direction])
/* 304 */         _touch_start_time = e.timeStamp
/* 305 */         _pos_before_touch = that.$slideline_inner.position()[that.animation_position]
/* 306 */       }.bind(this), false)
/* 307 */       el.addEventListener('touchmove', function(e){
/* 308 */         _diff = parseFloat(e[that.touch_direction]) - _touch_start_position
/* 309 */         _moved = Math.abs(_diff) > 0
/* 310 */         if(!_moved) return true
/* 311 */         e.preventDefault()
/* 312 */         that.slideline_inner.style[that.animation_position] = (_pos_before_touch + _diff) + 'px'
/* 313 */       }, false)
/* 314 */       el.addEventListener('touchend', function(e){
/* 315 */         var time,part,velocity
/* 316 */         if(_moved) {
/* 317 */           time= (e.timeStamp-_touch_start_time)/1000
/* 318 */           part = _diff/ that.el_effect_size
/* 319 */           velocity = that.velocity(time, part)
/* 320 */           if((velocity.x > that.swipe_velocity && part < 0) || part <= -0.7) {
/* 321 */             that.setSwipeAnimationSpeed()
/* 322 */             that.next()
/* 323 */           } else if(velocity.x > that.swipe_velocity || part >= 0.7) {
/* 324 */             that.setSwipeAnimationSpeed()
/* 325 */             that.prev()
/* 326 */           } else {
/* 327 */             that.returnSwipedSlide()
/* 328 */           }
/* 329 */           _moved = false
/* 330 */         }
/* 331 */       }, false)
/* 332 */       this.$element.addClass('vc-build')
/* 333 */       return this
/* 334 */     }
/* 335 */   // CAROUSEL PLUGIN DEFINITION
/* 336 */   // ==========================
/* 337 */ 
/* 338 */   var old = $.fn.carousel
/* 339 */ 
/* 340 */   $.fn.carousel = function (option) {
/* 341 */     return this.each(function () {
/* 342 */       var $this   = $(this)
/* 343 */       var data    = $this.data('vc.carousel')
/* 344 */       var options = $.extend({}, Carousel.DEFAULTS, $this.data(), typeof option == 'object' && option)
/* 345 */       var action  = typeof option == 'string' ? option : options.slide
/* 346 */ 
/* 347 */       if (!data) $this.data('vc.carousel', (data = new Carousel(this, options)))
/* 348 */       if (typeof option == 'number') data.to(option)
/* 349 */       else if (action) data[action]()
/* 350 */       else if (options.interval) data.pause().cycle()

/* vc_carousel.js */

/* 351 */     })
/* 352 */   }
/* 353 */ 
/* 354 */   $.fn.carousel.Constructor = Carousel
/* 355 */ 
/* 356 */ 
/* 357 */   // CAROUSEL NO CONFLICT
/* 358 */   // ====================
/* 359 */ 
/* 360 */   $.fn.carousel.noConflict = function () {
/* 361 */     $.fn.carousel = old
/* 362 */     return this
/* 363 */   }
/* 364 */ 
/* 365 */ 
/* 366 */   // CAROUSEL DATA-API
/* 367 */   // =================
/* 368 */ 
/* 369 */   $(document).off('click.vc.carousel.data-api').on('click.vc.carousel.data-api', '[data-slide], [data-slide-to]', function (e) {
/* 370 */     var $this   = $(this), href
/* 371 */     var $target = $($this.attr('data-target') || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '')) //strip for ie7
/* 372 */     var options = $.extend({}, $target.data(), $this.data())
/* 373 */     var slideIndex = $this.attr('data-slide-to')
/* 374 */     if (slideIndex) options.interval = false
/* 375 */     $target.carousel(options)
/* 376 */ 
/* 377 */     if (slideIndex = $this.attr('data-slide-to')) {
/* 378 */       $target.data('vc.carousel').to(slideIndex)
/* 379 */     }
/* 380 */ 
/* 381 */     e.preventDefault()
/* 382 */   })
/* 383 */ 
/* 384 */   $(window).on('load', function () {
/* 385 */     $('[data-ride="vc-carousel"]').each(function () {
/* 386 */       var $carousel = $(this)
/* 387 */       $carousel.carousel($carousel.data())
/* 388 */     })
/* 389 */   })
/* 390 */ 
/* 391 */ })(window.jQuery);
/* 392 */ 
