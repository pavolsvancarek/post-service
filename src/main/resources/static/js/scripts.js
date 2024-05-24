let existingIds = [];
let existingUserIds = [];

async function fetchExistingIds() {
    const response = await fetch('/api/posts/all');
    const posts = await response.json();
    existingIds = posts.map(post => post.id);
    existingUserIds = [...new Set(posts.map(post => post.userId))]; // Get unique user IDs
}

function getRandomUserId() {
    return Math.floor(Math.random() * 10) + 1; // Generates a random number between 1 and 10
}

function getRandomId() {
    return Math.floor(Math.random() * 100) + 1; // Generates a random number between 1 and 100
}

function getRandomTitle() {
    const titles = ["jablko", "hruška", "slivka", "marhuľa", "jahoda", "broskyňa", "hrozno", "banán", "pomaranč", "ananas"];
    return titles[Math.floor(Math.random() * titles.length)];
}

function getRandomBody() {
    const bodies = [
        "uhorka rajčina",
        "cibuľa cesnak",
        "cvikla zemiaky petržlen",
        "paradajka mozzarella bazalka",
        "kukurica fazuľa mrkva",
        "špenát brokolica kapusta",
        "tekvica pór červená paprika",
        "zeler reďkovka karfiol"
    ];
    return bodies[Math.floor(Math.random() * bodies.length)];
}

function getRandomExistingId() {
    return existingIds.length ? existingIds[Math.floor(Math.random() * existingIds.length)] : null;
}

function getRandomExistingUserId() {
    return existingUserIds.length ? existingUserIds[Math.floor(Math.random() * existingUserIds.length)] : null;
}

async function respondToRequest(response, elementId) {
    let result;
    try {
        result = await response.json();
    } catch (error) {
        result = { error: "An unexpected error occurred. Please try again later." };
    }

    if (response.ok) {
        if (Array.isArray(result)) {
            document.getElementById(elementId).innerHTML = generateTableHTML(result);
        } else {
            document.getElementById(elementId).innerHTML = generateTableHTML([result]);
        }
    } else if (response.status >= 400 && response.status < 500) {
        document.getElementById(elementId).innerHTML = formatErrorMessage(result);
    } else if (response.status >= 500) {
        document.getElementById(elementId).textContent = "Please specify the values to make this request.";
    }
}

function formatErrorMessage(error) {
    if (typeof error === 'object' && error !== null) {
        let formattedError = '<ul>';
        for (const [key, value] of Object.entries(error)) {
            formattedError += `<li><strong>${key}:</strong> ${value}</li>`;
        }
        formattedError += '</ul>';
        return formattedError;
    }
    return error;
}

async function addPost() {
    const userId = document.getElementById('add-userId').value;
    const title = document.getElementById('add-title').value;
    const body = document.getElementById('add-body').value;

    if (!userId && !title && !body) {
        document.getElementById('add-result').innerHTML = "<p>Please specify the values to make this request.</p>";
        return;
    }

    const response = await fetch('/api/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId, title, body })
    });

    await respondToRequest(response, 'add-result');
}

async function getPostById() {
    await fetchExistingIds();
    const id = document.getElementById('get-id').value;

    const response = await fetch(`/api/posts/${id}`);
    await respondToRequest(response, 'get-result');
}

async function getPostsByUserId() {
    await fetchExistingIds();
    const userId = document.getElementById('get-userId').value;

    const response = await fetch(`/api/posts/user/${userId}`);
    await respondToRequest(response, 'get-user-result');
}

async function updatePost() {
    await fetchExistingIds();
    const id = document.getElementById('update-id').value;
    const title = document.getElementById('update-title').value;
    const body = document.getElementById('update-body').value;

    const response = await fetch(`/api/posts/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, body })
    });

    await respondToRequest(response, 'update-result');
}

async function deletePost() {
    await fetchExistingIds();
    const id = document.getElementById('delete-id').value;

    const response = await fetch(`/api/posts/${id}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        document.getElementById('delete-result').textContent = "Post successfully deleted";
    } else {
        await respondToRequest(response, 'delete-result');
    }
}

function generateTableHTML(data) {
    if (data.length === 0) return "<p>No data available</p>";

    const headers = Object.keys(data[0]);
    let table = '<table class="table table-bordered"><thead><tr>';

    headers.forEach(header => {
        table += `<th>${header}</th>`;
    });
    table += '</tr></thead><tbody>';

    data.forEach(row => {
        table += '<tr>';
        headers.forEach(header => {
            table += `<td>${row[header]}</td>`;
        });
        table += '</tr>';
    });

    table += '</tbody></table>';
    return table;
}

// Function to fill the Add Post form with random data
function fillRandomAddPost() {
    document.getElementById('add-userId').value = getRandomUserId();
    document.getElementById('add-title').value = getRandomTitle();
    document.getElementById('add-body').value = getRandomBody();
}

// Function to fill the Get Post by ID form with random data
function fillRandomGetPostById() {
    document.getElementById('get-id').value = getRandomId();
}

// Function to fill the Get Posts by User ID form with random data
function fillRandomGetPostsByUserId() {
    const userId = getRandomExistingUserId();
    if (userId !== null) {
        document.getElementById('get-userId').value = userId;
    }
}

// Function to fill the Update Post form with random data
function fillRandomUpdatePost() {
    const id = getRandomExistingId();
    if (id !== null) {
        document.getElementById('update-id').value = id;
        document.getElementById('update-title').value = getRandomTitle();
        document.getElementById('update-body').value = getRandomBody();
    }
}

// Function to fill the Delete Post form with random data
function fillRandomDeletePost() {
    const id = getRandomExistingId();
    if (id !== null) {
        document.getElementById('delete-id').value = id;
    }
}

// Initialize existing IDs on page load
window.onload = async function() {
    await fetchExistingIds();
};