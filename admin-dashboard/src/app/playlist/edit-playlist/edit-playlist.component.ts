import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {AsyncPipe} from '@angular/common';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

@Component({
  selector: 'app-edit-playlist',
  standalone: true,
  imports: [MatDialogModule, FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatAutocompleteModule,
        ReactiveFormsModule,
        AsyncPipe,],
  templateUrl: './edit-playlist.component.html',
  styleUrl: './edit-playlist.component.css'
})
export class EditPlaylistComponent {

  myControl = new FormControl('');
  options: string[] = ['Son Tung MTP', 'Mono', 'Hieu Thu Hai','Son Tung MTP', 'Mono', 'Hieu Thu Hai','Son Tung MTP', 'Mono', 'Hieu Thu Hai','Son Tung MTP', 'Mono', 'Hieu Thu Hai'];
  filteredOptions: Observable<string[]> | undefined;

  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  //dialog
constructor(public dialogRef: MatDialogRef<EditPlaylistComponent>) {}

  saveGenre(): void {
    // Logic để lưu user mới
    console.log('User saved!');
    this.dialogRef.close('saved');
  }
}
