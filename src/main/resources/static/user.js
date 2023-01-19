const data = document.getElementById("data-user");
const url = 'http://localhost:8080/api/auth';

function userAuthInfo() {
    fetch(url)
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
            data.innerHTML = temp;
        });
}

userAuthInfo()
