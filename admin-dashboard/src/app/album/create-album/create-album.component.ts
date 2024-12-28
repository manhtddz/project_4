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
  selector: 'app-create-album',
  imports: [MatDialogModule, FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    AsyncPipe,],
  templateUrl: './create-album.component.html',
  styleUrl: './create-album.component.css'
})
export class CreateAlbumComponent {

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
constructor(public dialogRef: MatDialogRef<CreateAlbumComponent>) {}

  saveGenre(): void {
    // Logic để lưu user mới
    console.log('User saved!');
    this.dialogRef.close('saved');
  }

  
  // xu li anh tai len
  @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;
  selectedFile: File | null = null;
  previewUrl: string | null = null;
  imageError: boolean = false;

  // Kích hoạt file input
  triggerFileInput(): void {
    const fileInput = document.getElementById('imageUpload') as HTMLElement;
    fileInput.click();
  }

  // Xử lý file được chọn
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files[0]) {
      const file = input.files[0];

      if (file.type.startsWith('image/')) {
        this.selectedFile = file;
        this.imageError = false;

        // Tạo URL xem trước
        const reader = new FileReader();
        reader.onload = (e: ProgressEvent<FileReader>) => {
          this.previewUrl = e.target?.result as string;
        };
        reader.readAsDataURL(file);
      } else {
        this.imageError = true;
        this.previewUrl = null;
        alert('Please select a valid image file.');
      }
    } else {
      this.imageError = true;
      this.previewUrl = null;
    }
  }
}
