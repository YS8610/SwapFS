import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from '../Constants';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  telegramLink = Constants.telegramBotLink;

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  onAdd(){
    this.router.navigate(['add']);
  }

}
