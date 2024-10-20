function fetchUserData() {
    const token = localStorage.getItem('JWT_TOKEN');

    // Verify the user's token and get user details
    fetch('/api/users/getUserDetails', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const isAdmin = data.roles.some(role => role.name === 'ADMIN');
                const isUser = data.roles.some(role => role.name === 'USER');

                if (isAdmin) {
                    window.location.href = 'adminMenuPage.html';
                    return;
                }
                if (!isUser) {
                    window.location.href = 'index.html';
                }
            } else {
                console.error('Failed to fetch user details:', data.message);
                window.location.href = 'index.html';
            }
        })
        .catch(error => {
            console.error('Error fetching user details:', error);
            window.location.href = 'index.html';
        });
}

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
                localStorage.removeItem('JWT_TOKEN');
                window.location.href = 'index.html';
            } else {
                console.error('Failed to log out');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('JWT_TOKEN');
    if (!token) {
        window.location.href = 'index.html';
        return;
    }
    fetchUserData();
});