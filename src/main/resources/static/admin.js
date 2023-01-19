//Таблица всех пользователей
const url = 'http://localhost:8080/api/admin/users/';
const renderTable = document.getElementById("users-table-body");

const renderPosts = (allUsers) => {
    let temp = '';
    allUsers.forEach((u) => {
        temp += `<tr>
                                <td>${u.id}</td>
                                <td>${u.name}</td>
                                <td>${u.lastName}</td>
                                <td>${u.age}</td>
                                <td>${u.email}</td>
                                <td>${u.roles.map((el)=> el.name.substring(5)).join(', ')}</td>
                                <td>  
                                <button class="btn btn-info" type="button"
                                data-bs-toggle="modal" data-bs-target="#staticBackdropEdit"
                                onclick="editModal(${u.id})">Edit</button></td>
                                <td>
                                <button class="btn btn-danger" type="button"
                                data-bs-toggle="modal" data-bs-target="#staticBackdropDelete"
                                onclick="deleteModal(${u.id})">Delete</button></td>
                                </tr>
                                `
    })
    renderTable.innerHTML = temp;
}

function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            renderPosts(data)
        })
}

getAllUsers()

//Добавление нового пользователя
const addForm = document.getElementById("add-form");
addForm.addEventListener('submit', (e) => {
    e.preventDefault();
    let nameValue = document.getElementById("name").value;
    let lastNameValue = document.getElementById("lastName").value;
    let ageValue = document.getElementById("age").value;
    let emailValue = document.getElementById("email").value;
    let passwordValue = document.getElementById("password").value;
    let roles = getRoles(Array.from(document.getElementById("addRoles").selectedOptions).map(role => role.value));
    let newUser = {
        name: nameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }
    fetch(url, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(newUser)
    })
        .then(data => {
            const dataArr = [];
            dataArr.push(data);
            getAllUsers(data);
        }).then(() => {
            addForm.reset()
            document.getElementById("nav-home-tab").click();})
})
function getRoles(rols) {
    let roles = [];
    if (rols.indexOf("ROLE_ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    if (rols.indexOf("ROLE_USER") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

// Delete modal
function deleteModal(id) {
    fetch(url + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(us => {
            document.getElementById('idDelete').value = us.id;
            document.getElementById('nameDelete').value = us.name;
            document.getElementById('lastNameDelete').value = us.lastName;
            document.getElementById('ageDelete').value = us.age;
            document.getElementById('passwordDelete').value = us.password;
            document.getElementById('emailDelete').value = us.email;
        })
    });
}
// Delete user
const deleteUser = document.getElementById("deleteForm");
deleteUser.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(url + document.getElementById('idDelete').value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(() => {
        document.getElementById("closeDeleteForm").click();
        getAllUsers()
    })
})

// Edit modal
function editModal(id) {
    fetch(url + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(us => {

            document.getElementById('idEdit').value = us.id;
            document.getElementById('nameEdit').value = us.name;
            document.getElementById('lastNameEdit').value = us.lastName;
            document.getElementById('ageEdit').value = us.age;
            document.getElementById('passwordEdit').value = us.password;
            document.getElementById('emailEdit').value = us.email;

        })
    });
}

//Edit user
const editUser = document.getElementById("editForm");
editUser.addEventListener('submit', (e) => {
    e.preventDefault();
    let idValue = document.getElementById("idEdit").value;
    let nameValue = document.getElementById("nameEdit").value;
    let lastNameValue = document.getElementById("lastNameEdit").value;
    let ageValue = document.getElementById("ageEdit").value;
    let emailValue = document.getElementById("emailEdit").value;
    let passwordValue = document.getElementById("passwordEdit").value;
    let roles = editRoles(Array.from(document.getElementById("rolesEdit").selectedOptions).map(role => role.value));
    let newUser = {
        id: idValue,
        name: nameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }
    fetch(url + document.getElementById('idEdit').value, {
        method: "PATCH",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    }).then(() => {
        document.getElementById("closeEditForm").click();
        getAllUsers()
    })
})
function editRoles(rols) {
    let roles = [];
    if (rols.indexOf("ROLE_ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    if (rols.indexOf("ROLE_USER") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

//User
const tableForUser = document.getElementById("tableForUser");
const urlAuth = 'http://localhost:8080/api/auth';

function userAuthInfo() {
    fetch(urlAuth)
        .then((res) => res.json())
        .then((u) => {
            let temp = '';
            temp += `<tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.lastName}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td>${u.roles.map((el)=> el.name.substring(5)).join(', ')}</td>
            </tr>`;
            tableForUser.innerHTML = temp;
        });
}

userAuthInfo()


