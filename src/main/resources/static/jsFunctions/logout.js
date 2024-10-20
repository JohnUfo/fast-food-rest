function logout() {
    const token = localStorage.getItem('JWT_TOKEN');

    fetch('/api/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.ok) {
                // Remove the token from local storage after successful logout
                localStorage.removeItem('JWT_TOKEN');
                // Redirect to the login page
                window.location.href = 'index.html';
            } else {
                console.error('Failed to log out');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
