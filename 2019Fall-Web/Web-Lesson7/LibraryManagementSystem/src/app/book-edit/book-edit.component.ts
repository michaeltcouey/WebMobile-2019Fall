import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {ApiService} from '../api.service';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {
  book = {};
  updateBookForm: FormGroup;
  constructor(private router: Router, private route: ActivatedRoute, private api: ApiService, private formBuilder: FormBuilder) {
  }
  ngOnInit() {
    this.getBookDetails(this.route.snapshot.params['id']);
    this.updateBookForm = this.formBuilder.group({
      'id': [null, Validators.required],
      'isbn': [null, Validators.required],
      'title': [null, Validators.required],
      'description': [null, Validators.required],
      'author': [null, Validators.required],
      'publisher': [null, Validators.required],
      'published_year': [null, Validators.required]
    });
  }
  getBookDetails(id) {
    this.api.getBook(id)
      .subscribe(data => {
        console.log(data);
        this.book = data;
        this.updateBookForm = this.formBuilder.group({
          'id': [data._id, Validators.required],
          'isbn': [data.isbn, Validators.required],
          'title': [data.title, Validators.required],
          'description': [ data.description, Validators.required],
          'author': [data.author, Validators.required],
          'publisher': [data.publisher, Validators.required],
          'published_year': [data.published_year, Validators.required]
        });
      });
  }
  onFormSubmit(id, data) {
    this.api.updateBook(id, data)
      .subscribe(res => {
        console.log('before new_id');
        let new_id = res['_id'];
        console.log('before navigating');
        this.router.navigate(['/book-details', new_id]);
      }, (err) => {
        console.log(err);
      });
  }
}
