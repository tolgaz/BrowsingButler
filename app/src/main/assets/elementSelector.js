document.addEventListener('click', function (e) {
             e.stopPropagation();
             e = e || window.event;
             const target = e.target || e.srcElement;
             //text = target.textContent || target.innerText;
             target.style = "border-color: red; border-style: solid; border-width: 2; box-sizing: border-box";
             console.log( target.outerHTML);
             JSInterface.methodToCheck(target.outerHTML);
         }, true);