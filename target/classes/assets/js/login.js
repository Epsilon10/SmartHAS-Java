// Wait for the DOM to be ready
$(function() {
    // Initialize form validation on the registration form.
    // It has the name attribute "registration"

    $("form[name='login']").validate({
        errorPlacement: function(label, element) {
            element.addClass('form-control is-invalid');
            elementName = element.attr('name');

        },
        wrapper: 'span',
        // Specify validation rules
        rules: {
            // The key name on the left side is the name attribute
            // of an input field. Validation rules are defined
            // on the right side

            'username': {
                required: true


            },
            'password': {
                required: true,
                minlength: 5
            }
        },
        // Specify validation error messages
        //etetrer dfdsfd fdsa  fd
        messages: {
            'password': {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            },
            'username': "Please enter your username"
        },

        // Make sure the form is submitted to the destination defined
        // in the "action" attribute of the form when valid
        submitHandler: function(form) {
            form.submit();
        }
    });
});