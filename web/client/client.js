"use strict";

const socket = new WebSocket("ws://localhost:8888/game");
let mark, otherPlayerMark;
let isMyTurn;
let gameIsOver;
const upperHeader = document.querySelector('.upper-header');
const lowerHeader = document.querySelector('.lower-header');
const cells = document.querySelectorAll('.cell');
cells.forEach(item => item.addEventListener('click', makeMove));

socket.onopen = function wsOpen() {
    console.log('Connected');
}
socket.onclose = function wsClose() {
    socket.close();
}
socket.onerror = function wsError(error) {
    console.error(error);
}

socket.onmessage = function onMessage(message) {
    if (message.data === "PlayerX") {
        mark = 'X';
        otherPlayerMark = 'O';
        lowerHeader.innerHTML = "Hi! Your mark is Cross (X)";
        upperHeader.innerHTML = "Player X";
        setTimeout(function () {
            lowerHeader.innerHTML = "Waiting for Player O...";
        }, 2000);
    }
    if (message.data === "PlayerO") {
        mark = 'O';
        otherPlayerMark = 'X';
        lowerHeader.innerHTML = "Hi! Your mark is Nought (O)";
        upperHeader.innerHTML = "Player O";
        socket.send('Start');
    }
    if (message.data === "Start") {
        setTimeout(function () {
            lowerHeader.innerHTML = "Let's start!";
        }, 2000);
        if (mark === "X") {
            setTimeout(function () {
                lowerHeader.innerHTML = "Your turn. Choose a cell, please.";
            }, 2000);
        } else if (mark === "O") {
            setTimeout(function () {
                lowerHeader.innerHTML = "Waiting for Player X to move...";
            }, 2000);
        }
    }
    if (message.data === "YourTurn") {
        isMyTurn = true;
        lowerHeader.innerHTML = "Your turn. Choose a cell, please.";
    }

    if (message.data === "NotEmptyCell") {
        isMyTurn = true;
    }

    if (~message.data.indexOf("OppMove")) {
        let cellNumber = message.data.split(':')[1];
        let cell = document.querySelector(`[data-cell-number='${cellNumber}']`);
        markMove(cell, otherPlayerMark);
    }
    if (message.data === "Win") {
        gameIsOver = true;
        lowerHeader.innerHTML = "You win! Congratulations!";
    }
    if (message.data === "Lose") {
        gameIsOver = true;
        lowerHeader.innerHTML = "You lose. Try again :)";
    }
    if (message.data === "Draw") {
        gameIsOver = true;
        lowerHeader.innerHTML = "There's a draw. Try again!";
    }
}

function makeMove() {
    if (!isMyTurn) {
        return;
    }
    if (this.classList.contains('cellNought') || this.classList.contains('cellCross')) {
        return;
    }
    markMove(this, mark);
    setTimeout(function () {
        if (!gameIsOver) {
            lowerHeader.innerHTML = `Waiting for Player ${otherPlayerMark} move...`;
        }
    }, 0);
    socket.send(this.dataset.cellNumber);
    isMyTurn = false;

}

function markMove(cell, mark) {
    (mark === "X") ? cell.classList.add("cellCross") :
        cell.classList.add("cellNought");
}


