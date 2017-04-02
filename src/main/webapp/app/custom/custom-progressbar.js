function intialProgressbar(barID, progressNum, progressText, barID2, progressText2) {
    $(barID).html("");
    var circle = new ProgressBar.Circle(barID, {
        color: '#37c282',
        strokeWidth: 7,
        trailColor: '#b1b7be',
        trailWidth: 3,
        duration: 1000,
        text: {
            value: '0',

        },
        step: function(state, bar) {
            bar.setText(progressText);
        }
    });
    circle.animate(progressNum, function() {
    });

    $(barID2).html("");
    var circle2 = new ProgressBar.Circle(barID2, {
        color: '#37c282',
        strokeWidth: 7,
        trailColor: '#b1b7be',
        trailWidth: 3,
        duration: 1000,
        text: {
            value: '0',

        },
        step: function(state, bar) {
            bar.setText(progressText2);
        }
    });
    circle2.animate(0, function() {
    });
}

function intialProgressbar_today(barID, progressNum) {
    
}
