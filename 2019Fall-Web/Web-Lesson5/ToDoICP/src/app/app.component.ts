import { Component } from '@angular/core';
import {Items} from './items';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
 title = 'To-Do List 2019';
  // define list of items
  public items: Array<{itemNumber: number, description: string, complete: string, buttonText: string;}> = [
    {itemNumber: 1, description: 'item #1', complete: '', buttonText: 'Complete'},
    {itemNumber: 2, description: 'item #2', complete: '', buttonText: 'Complete'},
    {itemNumber: 3, description: 'item #3', complete: 'completed', buttonText: 'Re-Open'},
    {itemNumber: 4, description: 'item #4', complete:  '', buttonText: 'Complete'},
  ];

  // Write code to push new item
  submitNewItem() {
    // @ts-ignore
    const new_text: string = document.getElementById('newItemDescription').value;
    // @ts-ignore
    document.getElementById('newItemDescription').value = '';
    this.items.push({
      itemNumber: (this.items.length + 1),
      description:  new_text,
      complete: '',
      buttonText: 'Complete'
    });
  }

  // Write code to complete item
  completeItem(item_2_complete: number) {
  if (this.items[item_2_complete - 1 ].buttonText === 'Re-Open') {
    this.items[item_2_complete - 1 ].buttonText = 'Complete';
    this.items[item_2_complete - 1 ].complete = '';
  } else {
    this.items[item_2_complete - 1 ].buttonText = 'Re-Open';
    this.items[item_2_complete - 1 ].complete = 'completed';
  }
  }

  // Write code to delete item
  deleteItem(num_2_remove: number) {
   this.items.splice(num_2_remove, 1);
  }

}
