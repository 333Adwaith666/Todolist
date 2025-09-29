document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('form[action*="/delete"]').forEach(form => {
        form.addEventListener('submit', e => {
            if(!confirm('Are you sure?')) e.preventDefault();
        });
    });
});
