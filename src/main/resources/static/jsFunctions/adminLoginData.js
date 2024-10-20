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

                if (isUser) {
                    return window.location.href = 'userMenuPage.html';
                }
                if (!isAdmin) {
                    return window.location.href = 'index.html';
                }
            } else {
                console.error('Failed to fetch user details:', data.message);
                return window.location.href = 'index.html';
            }
        })
        .catch(error => {
            console.error('Error fetching user details:', error);
            return window.location.href = 'index.html';
        });
}

document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('JWT_TOKEN');
    if (!token) {
        return window.location.href = 'index.html';
    }
    fetchUserData();
});
