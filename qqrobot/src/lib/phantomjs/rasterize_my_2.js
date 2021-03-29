var page = require('webpage').create(),
    system = require('system'),
    address, output, size, pageWidth, pageHeight, pageTimeout;
page.settings.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36";

if (system.args.length < 3 || system.args.length > 6) {
    console.log ("Arguments length error!");
    phantom.exit(1);
} else {
    address = system.args[1];
    console.log ("address:",address);
    output = system.args[2];
    console.log ("output:",output);

    page.viewportSize = { width: 600, height: 600 };
    if (system.args.length > 3 && system.args[3].substr(-2) === "px") {
        size = system.args[3].split('*');
        if (size.length === 2) {
            pageWidth = parseInt(size[0], 10);
            pageHeight = parseInt(size[1], 10);
            page.viewportSize = { width: pageWidth, height: pageHeight };
            page.clipRect = { top: 0, left: 0, width: pageWidth, height: pageHeight };
        } else {
            //console.log("size:", system.args[3]);
            pageWidth = parseInt(system.args[3], 10);
            pageHeight = parseInt(pageWidth * 3/4, 10); // it's as good an assumption as any
            //console.log ("pageHeight:",pageHeight);
            page.viewportSize = { width: pageWidth, height: pageHeight };
        }
    }
    console.log ("page.viewportSize:",page.viewportSize.width,"*",page.viewportSize.height,"px");

    if (system.args.length > 4) {
        page.zoomFactor = system.args[4];
    }
    console.log ("page.zoomFactor:",page.zoomFactor);

    if(system.args.length > 5){
        pageTimeout = parseInt(system.args[5]);
    } else {
        pageTimeout = 1000;
    }
    console.log ("pageTimeout:",pageTimeout);

    page.open(address, function (status) {
        if (status !== 'success') {
            console.log('Unable to load the address!');
            phantom.exit(1);
        } else {
            window.setTimeout(function () {
                page.render(output);
                phantom.exit();
            }, pageTimeout);
        }
    });
}