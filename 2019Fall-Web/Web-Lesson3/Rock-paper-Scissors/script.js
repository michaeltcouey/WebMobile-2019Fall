const results = document.getElementById("results");
const choices = document.getElementById("choices");
const button = document.getElementsByClassName("button");

clear();
function clear() {
    results.innerHTML = "";
    choices.innerHTML = "";
}
function getComputerChoice() {
    switch(Math.floor(Math.random()*3)) {
        case 0:
            return '<img style="height: 75px; width:75px; object-fit: contain" src="Rock.png" alt="rock">';
        case 1:
            return '<img style="height: 75px; width:75px; object-fit: contain" src="Paper.png" alt="paper">';
        case 2:
            return '<img style="height: 75px; width:75px; object-fit: contain" src="Scissors.png" alt="scissors">';
    }
}

for (let i = 0; i < button.length; i++) {
    button[i].addEventListener('click', function() {
        switch (this.innerHTML) {
            case "Clear": {
                clear();
                break;
            }
            case "Play": {
                if ((results.innerHTML === "")&& choices.innerHTML !== "" ){
                    const userChoice = choices.innerHTML;
                    const computerChoice = getComputerChoice();
                    choices.innerHTML += computerChoice;
                    results.innerHTML += determineTheWinner(userChoice, computerChoice);
                    break;
                }
                else {
                    clear();
                    break;
                }
            }
            default: {
                clear();
                choices.innerHTML += this.innerHTML;
                break;
            }
        }
    });
}

function determineTheWinner(userChoice,computerChoice) {
    if (userChoice === computerChoice) {
        return 'It\'s a tie!';
    }
    else if (userChoice === '<img style="height: 75px; width:75px; object-fit: contain" src="Rock.png" alt="rock">') {
        if (computerChoice === '<img style="height: 75px; width:75px; object-fit: contain" src="Paper.png" alt="paper">') {
            return 'Computer wins!';
        }
        else {
            return 'You win!';
        }
    }
    else if (userChoice === '<img style="height: 75px; width:75px; object-fit: contain" src="Paper.png" alt="paper">') {
        if (computerChoice === '<img style="height: 75px; width:75px; object-fit: contain" src="Scissors.png" alt="scissors">') {
            return 'Computer wins!';
        }
        else {
            return 'You win!';
        }
    }
    else if (userChoice === '<img style="height: 75px; width:75px; object-fit: contain" src="Scissors.png" alt="scissors">') {
        if (computerChoice === '<img style="height: 75px; width:75px; object-fit: contain" src="Rock.png" alt="rock">') {
            return 'Computer wins!';
        }
        else {
            return 'You win!';
        }
    }
}

