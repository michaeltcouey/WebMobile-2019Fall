import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'CalculatorICP';
  /*public equation = document.getElementById('equation');
  public number = document.getElementById('number');
  public button = document.getElementsByClassName('button');
  */
  public elementsArray = [];
  clear() {
    document.getElementById('equation').innerHTML = '';
    document.getElementById('number').innerHTML = '';
    this.elementsArray = [];
  }

  clearNumber() {
    document.getElementById('number').innerHTML = '';
  }

  equationAdd(a, b: string) {
    this.elementsArray.push(a);
    this.elementsArray.push(b);
    document.getElementById('equation').innerHTML += a;
    document.getElementById('equation').innerHTML += b;
  }

  multiply(a, b) {
    const c = this.elementsArray[a] * this.elementsArray[b];
    this.elementsArray[a] = c.toString();
    this.elementsArray.splice(a + 1, 2);
  }

  divide(a, b) {
    const c = this.elementsArray[a] / this.elementsArray[b];
    this.elementsArray[a] = c.toString();
    this.elementsArray.splice(a + 1, 2);
  }

  modulo(a, b) {
    const c = this.elementsArray[a] % this.elementsArray[b];
    this.elementsArray[a] = c.toString();
    this.elementsArray.splice(a + 1, 2);
  }

  add(a, b) {
    const c: number = this.elementsArray[a] - (this.elementsArray[b] * -1);
    this.elementsArray[a] = c.toString();
    this.elementsArray.splice(a + 1, 2);
  }

  subtract(a, b) {
    const c: number = this.elementsArray[a] - this.elementsArray[b];
    this.elementsArray[a] = c.toString();
    this.elementsArray.splice(a + 1, 2);
  }

  calculate() {
    for (let i = 0; i < this.elementsArray.length; i++) {
      if (this.elementsArray[i] === '*') {
        this.multiply(i - 1, i + 1);
        i = i - 2;
      } else if (this.elementsArray[i] === '/') {
        this.divide(i - 1, i + 1);
        i = i - 2;
      }
    }
    for (let i = 0; i < this.elementsArray.length; i++) {
      if (this.elementsArray[i] === '+') {
        this.add(i - 1, i + 1);
        i = i - 2;
      } else if (this.elementsArray[i] === '-') {
        this.subtract(i - 1, i + 1);
        i = i - 2;
      }
    }
  }

  buttonClick(selection: string) {
    const self = this;
      switch (selection) {
        case '+': {
          self.equationAdd(document.getElementById('number').innerHTML, '+');
          self.clearNumber();
          break;
        }
        case '-': {
          self.equationAdd(document.getElementById('number').innerHTML, '-');
          self.clearNumber();
          break;
        }
        case '*': {
          self.equationAdd(document.getElementById('number').innerHTML, '*');
          self.clearNumber();
          break;
        }
        case '/': {
          self.equationAdd(document.getElementById('number').innerHTML, '/');
          self.clearNumber();
          break;
        }
        case 'Clear': {
          self.clear();
          break;
        }
        case '=': {
          self.equationAdd(document.getElementById('number').innerHTML, '=');
          self.elementsArray.pop();
          self.calculate();
          document.getElementById('number').innerHTML = self.elementsArray[0];
          document.getElementById('equation').innerHTML = '';
          self.elementsArray.pop();
          break;
        }
        default: {
          document.getElementById('number').innerHTML += selection;
          break;
        }
      }
  }
}
