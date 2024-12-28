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
  selector: 'app-edit-song',
  imports: [MatDialogModule, FormsModule,
            MatFormFieldModule,
            MatInputModule,
            MatAutocompleteModule,
            ReactiveFormsModule,
            AsyncPipe,],
  templateUrl: './edit-song.component.html',
  styleUrl: './edit-song.component.css'
})
export class EditSongComponent {

  // Reactive Form Group
  form: FormGroup;

  // Autocomplete controls
  myControl = new FormControl('');
  options: string[] = ['Son Tung MTP', 'Mono', 'Hieu Thu Hai'];
  filteredOptions: Observable<string[]> | undefined;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<EditSongComponent>
  ) {
    // Initialize form group
    this.form = this.fb.group({
      name: ['', Validators.required],       // Username field
      songTitle: ['', Validators.required], // Song Title field
    });
  }

  ngOnInit() {
    // Setup filtered options for autocomplete
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || ''))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  saveUser(): void {
    if (this.form.valid) {
      console.log('Form submitted:', this.form.value);
      this.dialogRef.close('saved');
    } else {
      console.error('Form is invalid');
    }
  }
}
