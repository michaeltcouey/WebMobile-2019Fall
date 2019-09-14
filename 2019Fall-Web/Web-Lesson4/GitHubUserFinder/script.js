function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    let xmlhttpreq = new XMLHttpRequest();
    let apiurl = `https://api.github.com/users/${user}`
    xmlhttpreq.open('GET', apiurl, false );

    xmlhttpreq.send('');
    // The function should finally return the object(it now contains the response!)
    return xmlhttpreq;
}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    console.log(user);
    let usersName = user.name;
    let usersID   = user.id;
    let usersPicURL  = user.avatar_url;
    var userPic = new Image();
         userPic.src = user.avatar_url;
    let usersUrlText  = "User's URL";
    var userURL = usersUrlText.link(user.url);

    let profile = document.getElementById("profile");
    profile.getElementsByTagName("h2")[0].innerText = `User Name: = ${usersName} ID: ${usersID}`;
    profile.getElementsByClassName("avatar")[0].innerHTML = "";
    profile.getElementsByClassName("avatar")[0].appendChild(userPic);
    profile.getElementsByClassName("information")[0].innerHTML = userURL;


}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    console.log("USER NOT FOUND");
    let profile = document.getElementById("profile");
    profile.getElementsByTagName("h2")[0].innerText =`User: "${username}" Not Found`;
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
