import { Link } from 'react-router-dom'
import { BackendHealthCard } from '../components/BackendHealthCard'

type DashboardMetric = {
  label: string
  value: string
  tone: 'neutral' | 'attention' | 'success' | 'muted'
}

const dashboardMetrics: DashboardMetric[] = [
  { label: 'Open requests', value: '0', tone: 'neutral' },
  { label: 'Urgent issues', value: '0', tone: 'attention' },
  { label: 'Completed this month', value: '0', tone: 'success' },
  { label: 'Average resolution', value: 'N/A', tone: 'muted' },
]

export function HomePage() {
  return (
    <div className="app-shell">
      <header className="top-bar">
        <Link className="brand" to="/" aria-label="SafeRent home">
          <span className="brand-mark">SR</span>
          <span>SafeRent</span>
        </Link>

        <nav className="main-nav" aria-label="Primary navigation">
          <Link aria-current="page" to="/">
            Home
          </Link>
        </nav>
      </header>

      <main className="workspace">
        <section className="page-heading" aria-labelledby="home-title">
          <p className="eyebrow">Tenant maintenance tracker</p>
          <h1 id="home-title">Maintenance requests</h1>
          <p>
            Track rental repairs across tenants, units, property managers, and
            technicians.
          </p>
        </section>

        <BackendHealthCard />

        <section className="metric-grid" aria-label="Maintenance summary">
          {dashboardMetrics.map((metric) => (
            <article
              className={`metric-card metric-card-${metric.tone}`}
              key={metric.label}
            >
              <span>{metric.label}</span>
              <strong>{metric.value}</strong>
            </article>
          ))}
        </section>

        <section className="request-panel" aria-labelledby="queue-title">
          <div>
            <p className="eyebrow">Request queue</p>
            <h2 id="queue-title">No maintenance requests yet</h2>
          </div>
          <p>
            New requests will appear here once tenants submit maintenance
            tickets.
          </p>
        </section>
      </main>
    </div>
  )
}
