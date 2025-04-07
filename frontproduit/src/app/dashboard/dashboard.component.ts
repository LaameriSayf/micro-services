import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { ProduitService } from '../services/produit.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  productStats: any[] = []; // To hold product statistics by category

  constructor(private productService: ProduitService) {}

  ngOnInit(): void {
    // Fetch product stats and render the chart
    this.productService.getProductStats().subscribe(stats => {
      this.productStats = stats;
      this.renderChart();
    });
  }

  renderChart(): void {
    const labels = this.productStats.map(stat => stat.categoryName);
    const data = this.productStats.map(stat => stat.productCount);

    // Create the chart with the fetched data
    new Chart('productChart', {
      type: 'bar', // Chart type
      data: {
        labels: labels,
        datasets: [{
          label: 'Number of Products per Category',
          data: data,
          backgroundColor: 'rgba(54, 162, 235, 0.2)', // Bar color
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }
}
