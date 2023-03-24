$(document).ready(function() {
    var dropdown = $('#country');
    $.ajax({
        url: '/content/dam/anf-code-challenge/exercise-1/countries.json',
        type: 'get',
        dataType: 'json',
        success: function(results) {
            $.each(results, function(key, entry) {
                dropdown.append($('<option></option>').attr('value', entry).text(key));
            });
        }
    });
    $("form").on("submit", function(e) {

        var formData = {
            'fname': $('#fname').val(),
            'lname': $('#lname').val(),
            'age': $('#age').val(),
            'country': $('#country').val()
        };
        $.ajax({
            url: "/etc/age.json",
            success: function(data) {
                if ((Number(formData.age) >= Number(data.minAge)) &&
                    (Number(formData.age) <= Number(data.maxAge)))
                    submitForm(formData);
                else
                    $('.age-error').show();
            },
            error: function() {
                alert('Something went wrong, Please try later');
            }
        });

        e.preventDefault();
    });

    function submitForm(formData) {
        $('.age-error').hide();
        $.ajax({
            url: '/bin/saveUserDetails',
            type: 'post',
            data: formData,

            success: function(results) {
                $('#successfully_submitted').show();
            },
            error: function(results) {
                $('#submition_failed').show();
            }
        });

    }
});