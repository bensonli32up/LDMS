$(document).ready(function() {
    $('#createScheduleForm').submit(function(event) {
        event.preventDefault();
        const data = {
            assetCost: $('#assetCost').val(),
            deposit: $('#deposit').val(),
            interestRate: $('#interestRate').val(),
            numberOfPayments: $('#numberOfPayments').val(),
            balloonPayment: $('#balloonPayment').val()
        };

        $.ajax({
            type: 'POST',
            url: '/service/amortization-schedules/',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                alert('Schedule created successfully!');
                $('#scheduleResult').html(JSON.stringify(response, null, 2));
            },
            error: function(xhr, status, error) {
                alert('Error creating schedule: ' + error);
            }
        });
    });

    $('#displayAllSchedules').click(function() {
        $.ajax({
            type: 'GET',
            url: '/service/amortization-schedules/',
            success: function(response) {
                $('#scheduleResult').html(JSON.stringify(response, null, 2));
            },
        });
    });

    $('#findScheduleById').click(function() {
        const id = $('#scheduleId').val();
        $.ajax({
            type: 'GET',
            url: `/service/amortization-schedules/${id}`,
            success: function(response) {
                $('#scheduleResult').html(JSON.stringify(response, null, 2));
            },
            error: function(xhr, status, error) {
                $('#scheduleResult').html('Schedule not found.');
            }
        });
    });
});