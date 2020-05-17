package com.lenovo.btopic12.bean;

import java.util.List;

public class AllProductionProcessesBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":25278,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":35},{"id":25277,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":34},{"id":25276,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":33},{"id":25275,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":32},{"id":25274,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":31},{"id":25273,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":30},{"id":25272,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":29},{"id":25271,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":28},{"id":25270,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":27},{"id":25269,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":26},{"id":25268,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":-1},{"id":25267,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":24},{"id":25266,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":23},{"id":25265,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":22},{"id":25264,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":21},{"id":25263,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":20},{"id":25262,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":19},{"id":25261,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":18},{"id":25260,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":17},{"id":25259,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":16},{"id":25258,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":15},{"id":25257,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":14},{"id":25256,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":13},{"id":25255,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":12},{"id":25254,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":11},{"id":25253,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":10},{"id":25252,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":9},{"id":25251,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":8},{"id":25250,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":7},{"id":25249,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":6},{"id":25248,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":-1},{"id":25247,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":44},{"id":25246,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":43},{"id":25245,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":42},{"id":25244,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":41},{"id":25243,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":40},{"id":25242,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":39},{"id":25241,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":38},{"id":25240,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":37},{"id":25239,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":36},{"id":25238,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":35},{"id":25237,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":34},{"id":25236,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":33},{"id":25235,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":32},{"id":25234,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":31},{"id":25233,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":30},{"id":25232,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":29},{"id":25231,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":28},{"id":25230,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":27},{"id":25229,"userWorkId":1,"userProductionLineId":2688,"nextUserPlStepId":26},{"id":25279,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":36},{"id":25280,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":37},{"id":25281,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":38},{"id":25282,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":39},{"id":25283,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":40},{"id":25284,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":41},{"id":25285,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":42},{"id":25286,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":43},{"id":25287,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":44},{"id":25288,"userWorkId":1,"userProductionLineId":2690,"nextUserPlStepId":-1},{"id":25289,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":26},{"id":25290,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":27},{"id":25291,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":28},{"id":25292,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":29},{"id":25293,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":30},{"id":25294,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":31},{"id":25295,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":32},{"id":25296,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":33},{"id":25297,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":34},{"id":25298,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":35},{"id":25299,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":36},{"id":25300,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":37},{"id":25301,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":38},{"id":25302,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":39},{"id":25303,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":40},{"id":25304,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":41},{"id":25305,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":42},{"id":25306,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":43},{"id":25307,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":44},{"id":25308,"userWorkId":1,"userProductionLineId":2691,"nextUserPlStepId":-1},{"id":25309,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":6},{"id":25310,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":7},{"id":25311,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":8},{"id":25312,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":9},{"id":25313,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":10},{"id":25314,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":11},{"id":25315,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":12},{"id":25316,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":13},{"id":25317,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":14},{"id":25318,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":15},{"id":25319,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":16},{"id":25320,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":17},{"id":25321,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":18},{"id":25322,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":19},{"id":25323,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":20},{"id":25324,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":21},{"id":25325,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":22},{"id":25326,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":23},{"id":25327,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":24},{"id":25328,"userWorkId":1,"userProductionLineId":2692,"nextUserPlStepId":-1},{"id":25329,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":26},{"id":25330,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":27},{"id":25331,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":28},{"id":25332,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":29},{"id":25333,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":30},{"id":25334,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":31},{"id":25335,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":32},{"id":25336,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":33},{"id":25337,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":34},{"id":25338,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":35},{"id":25339,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":36},{"id":25340,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":37},{"id":25341,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":38},{"id":25342,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":39},{"id":25343,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":40},{"id":25344,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":41},{"id":25345,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":42},{"id":25346,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":43},{"id":25347,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":44},{"id":25348,"userWorkId":1,"userProductionLineId":2693,"nextUserPlStepId":-1},{"id":25349,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":26},{"id":25350,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":27},{"id":25351,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":28},{"id":25352,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":29},{"id":25353,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":30},{"id":25354,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":31},{"id":25355,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":32},{"id":25356,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":33},{"id":25357,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":34},{"id":25358,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":35},{"id":25359,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":36},{"id":25360,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":37},{"id":25361,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":38},{"id":25362,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":39},{"id":25363,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":40},{"id":25364,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":41},{"id":25365,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":42},{"id":25366,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":43},{"id":25367,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":44},{"id":25368,"userWorkId":1,"userProductionLineId":2694,"nextUserPlStepId":-1},{"id":25369,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":46},{"id":25370,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":47},{"id":25371,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":48},{"id":25372,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":49},{"id":25373,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":50},{"id":25374,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":51},{"id":25375,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":52},{"id":25376,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":53},{"id":25377,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":54},{"id":25378,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":55},{"id":25379,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":56},{"id":25380,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":57},{"id":25381,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":58},{"id":25382,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":59},{"id":25383,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":60},{"id":25384,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":61},{"id":25385,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":62},{"id":25386,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":63},{"id":25387,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":64},{"id":25388,"userWorkId":1,"userProductionLineId":2695,"nextUserPlStepId":-1},{"id":25389,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":26},{"id":25390,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":27},{"id":25391,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":28},{"id":25392,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":29},{"id":25393,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":30},{"id":25394,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":31},{"id":25395,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":32},{"id":25396,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":33},{"id":25397,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":34},{"id":25398,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":35},{"id":25399,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":36},{"id":25400,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":37},{"id":25401,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":38},{"id":25402,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":39},{"id":25403,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":40},{"id":25404,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":41},{"id":25405,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":42},{"id":25406,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":43},{"id":25407,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":44},{"id":25408,"userWorkId":1,"userProductionLineId":2696,"nextUserPlStepId":-1},{"id":25409,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":26},{"id":25410,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":27},{"id":25411,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":28},{"id":25412,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":29},{"id":25413,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":30},{"id":25414,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":31},{"id":25415,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":32},{"id":25416,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":33},{"id":25417,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":34},{"id":25418,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":35},{"id":25419,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":36},{"id":25420,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":37},{"id":25421,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":38},{"id":25422,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":39},{"id":25423,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":40},{"id":25424,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":41},{"id":25425,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":42},{"id":25426,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":43},{"id":25427,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":44},{"id":25428,"userWorkId":1,"userProductionLineId":2697,"nextUserPlStepId":-1},{"id":25429,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":26},{"id":25430,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":27},{"id":25431,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":28},{"id":25432,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":29},{"id":25433,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":30},{"id":25434,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":31},{"id":25435,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":32},{"id":25436,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":33},{"id":25437,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":34},{"id":25438,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":35},{"id":25439,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":36},{"id":25440,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":37},{"id":25441,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":38},{"id":25442,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":39},{"id":25443,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":40},{"id":25444,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":41},{"id":25445,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":42},{"id":25446,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":43},{"id":25447,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":44},{"id":25448,"userWorkId":1,"userProductionLineId":2698,"nextUserPlStepId":-1},{"id":25449,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":26},{"id":25450,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":27},{"id":25451,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":28},{"id":25452,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":29},{"id":25453,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":30},{"id":25454,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":31},{"id":25455,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":32},{"id":25456,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":33},{"id":25457,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":34},{"id":25458,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":35},{"id":25459,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":36},{"id":25460,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":37},{"id":25461,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":38},{"id":25462,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":39},{"id":25463,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":40},{"id":25464,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":41},{"id":25465,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":42},{"id":25466,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":43},{"id":25467,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":44},{"id":25468,"userWorkId":1,"userProductionLineId":2699,"nextUserPlStepId":-1},{"id":25469,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":46},{"id":25470,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":47},{"id":25471,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":48},{"id":25472,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":49},{"id":25473,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":50},{"id":25474,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":51},{"id":25475,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":52},{"id":25476,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":53},{"id":25477,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":54},{"id":25478,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":55},{"id":25479,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":56},{"id":25480,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":57},{"id":25481,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":58},{"id":25482,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":59},{"id":25483,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":60},{"id":25484,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":61},{"id":25485,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":62},{"id":25486,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":63},{"id":25487,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":64},{"id":25488,"userWorkId":1,"userProductionLineId":2700,"nextUserPlStepId":-1},{"id":25489,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":26},{"id":25490,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":27},{"id":25491,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":28},{"id":25492,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":29},{"id":25493,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":30},{"id":25494,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":31},{"id":25495,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":32},{"id":25496,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":33},{"id":25497,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":34},{"id":25498,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":35},{"id":25499,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":36},{"id":25500,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":37},{"id":25501,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":38},{"id":25502,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":39},{"id":25503,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":40},{"id":25504,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":41},{"id":25505,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":42},{"id":25506,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":43},{"id":25507,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":44},{"id":25508,"userWorkId":1,"userProductionLineId":2701,"nextUserPlStepId":-1},{"id":25509,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":26},{"id":25510,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":27},{"id":25511,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":28},{"id":25512,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":29},{"id":25513,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":30},{"id":25514,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":31},{"id":25515,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":32},{"id":25516,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":33},{"id":25517,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":34},{"id":25518,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":35},{"id":25519,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":36},{"id":25520,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":37},{"id":25521,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":38},{"id":25522,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":39},{"id":25523,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":40},{"id":25524,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":41},{"id":25525,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":42},{"id":25526,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":43},{"id":25527,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":44},{"id":25528,"userWorkId":1,"userProductionLineId":2702,"nextUserPlStepId":-1}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 25278
         * userWorkId : 1
         * userProductionLineId : 2690
         * nextUserPlStepId : 35
         */

        private int id;
        private int userWorkId;
        private int userProductionLineId;
        private int nextUserPlStepId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(int userWorkId) {
            this.userWorkId = userWorkId;
        }

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public int getNextUserPlStepId() {
            return nextUserPlStepId;
        }

        public void setNextUserPlStepId(int nextUserPlStepId) {
            this.nextUserPlStepId = nextUserPlStepId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", userProductionLineId=" + userProductionLineId +
                    ", nextUserPlStepId=" + nextUserPlStepId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllProductionProcessesBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
