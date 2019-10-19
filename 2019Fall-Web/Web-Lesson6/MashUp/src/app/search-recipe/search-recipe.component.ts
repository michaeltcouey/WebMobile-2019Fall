import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpClientModule, HttpClientJsonpModule} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})

export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: string;
  placeValue: string;
  venueList = [];
  recipeList = [];

  isLoading = true;
  currentLat: any;
  currentLong: any;
  geolocationPosition: any;
  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      /**
       * Write code to get recipe
       */
      const ID = '35225e0d';
      const KEY = '6f43f46a2f6ac5cb47387e7f9207c63d';
      const BASE_URL = 'https://api.edamam.com/search';
      this._http.jsonp(`${BASE_URL}?q=${this.recipeValue}&app_id=${ID}&app_key=${KEY}`, 'callback')
        .subscribe((data: any) => {
          this.isLoading = false;
          this.recipeList = Object.keys(data.hits).map(function (k) {
            const i = data.hits[k];
            return {name: i.recipe.label, icon: i.recipe.image, url: i.recipe.url};
          });
           console.log(data);
        });

    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      /**
       * Write code to get place
       */
      const Client_ID = 'SYGCGGW5J2GVKSATQTIUTVSHUYNS1R1K1MGGDIK5LU00M4JA';
      const Client_Secret = 'B4DTMIFDCKH4J2C4ZSRGOVCJTU2JIF3K2T5KSFAYELDITY1D';
      const Base_URL = 'https://api.foursquare.com/v2/venues/explore';
      const limit = 10;
      // tslint:disable-next-line:max-line-length
      this._http.jsonp(`${Base_URL}?client_id=${Client_ID}&client_secret=${Client_Secret}&v=20180323&limit=${limit}&ll=${this.currentLat},${this.currentLong}&query=${this.placeValue}` , 'callback')
        .subscribe((data: any) => {
            this.isLoading = false;
            this.venueList = Object.keys(data.response.groups[0].items).map(function (k) {
                  const i = data.response.groups[0].items[k];
              console.log(i);
                return {name: i.venue.name,
                          location: i.venue.location,
                          currentLat: i.venue.location.lat,
                          currentLong: i.venue.location.lng
                          };
            });
          console.log(data);
        });
    }
  }
}
