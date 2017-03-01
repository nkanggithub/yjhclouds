<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<title>附近人员分布情况</title>
<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>
</head>
<body style="margin-left:auto;margin-right:auto;">
<div id="chart-container">FusionCharts will render here</div>
<script>
FusionCharts.ready(function () {
    var budgetChart = new FusionCharts({
        type: 'radar',
        renderAt: 'chart-container',
        width: '100%',
        height: '450',
        dataFormat: 'json',
        dataSource: {
    "chart": {
        "caption": "附近人员【32/170】分布情况",
        "bgcolor": "FFFFFF",
        "radarfillcolor": "FFFFFF",
        "plotfillalpha": "5",
        "plotborderthickness": "2",
        "anchoralpha": "500",
        "numberprefix": "",
        "numdivlines": "2",
        "legendposition": "bottom",
        "showborder": "0"
    },
    "categories": [
        {
            "font": "Arial",
            "fontsize": "11",
            "category": [
                {
                    "label": "北",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "西北",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "西",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "西南",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "南",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "东南",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "东",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "东北",
                    "font": "Arial",
                    "fontsize": "11"
                }
               
            ]
        }
    ],
    "dataset": [
     {
            "seriesname": "10km以内",
            "color": "0099FF",
            "anchorsides": "10",
            "anchorbordercolor": "0099FF",
            "anchorgbalpha": "0",
            "anchorradius": "2",
            "data": [
                {
                    "value": "8"
                },
                {
                    "value": "23"
                },
                {
                    "value": "13"
                },
                {
                    "value": "16"
                },
                {
                    "value": "11"
                },
                {
                    "value": "14"
                },
                {
                    "value": "10"
                },
                {
                    "value": "45"
                },
                {
                    "value": "23"
                },
                {
                    "value": "45"
                },
                {
                    "value": "23"
                },
                {
                    "value": "43"
                }
            ]
        },
        {
            "seriesname": "50km以内",
            "color": "CD6AC0",
            "anchorsides": "6",
            "anchorradius": "2",
            "anchorbordercolor": "CD6AC0",
            "anchorgbalpha": "0",
            "data": [
                {
                    "value": "50"
                },
                {
                    "value": "60"
                },
                {
                    "value": "48"
                },
                {
                    "value": "47"
                },
                {
                    "value": "37"
                },
                {
                    "value": "45"
                },
                {
                    "value": "36"
                },
                {
                    "value": "75"
                },
                {
                    "value": "35"
                },
                {
                    "value": "65"
                },
                {
                    "value": "34"
                },
                {
                    "value": "35"
                }
            ]
        }
       
    ]
}
    }).render();
});
</script>
</body>
</html>