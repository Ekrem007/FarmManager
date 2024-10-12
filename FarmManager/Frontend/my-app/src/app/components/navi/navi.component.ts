import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-navi',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navi.component.html',
  styleUrl: './navi.component.css'
})
export class NaviComponent implements OnInit {
  showNavbar: boolean = true;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.showNavbar = event.url !== '/login'; 
      }
    });
  }

}
