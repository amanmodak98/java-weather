async function fetchWeather(city){
  const res = await fetch(`/api/weather?city=${encodeURIComponent(city)}`);
  if(!res.ok){
    const err = await res.json().catch(()=>({message:res.statusText}));
    throw new Error(err.message || 'Request failed');
  }
  return res.json();
}

function setStatus(msg, tone='info'){
  const el=document.getElementById('status');
  el.textContent=msg||'';
  el.style.color = tone==='error' ? '#fecaca' : '#93c5fd';
}

function iconFromDescription(desc=''){
  const d=desc.toLowerCase();
  if(d.includes('snow')) return '#icon-snow';
  if(d.includes('rain')||d.includes('drizzle')) return '#icon-rain';
  if(d.includes('cloud')) return '#icon-cloud';
  if(d.includes('sun')||d.includes('clear')) return '#icon-sun';
  return '#icon-cloud';
}

function showCurrent(data){
  document.getElementById('location').textContent=data.location;
  document.getElementById('temp').textContent=Number.isFinite(data.temperatureC)?data.temperatureC.toFixed(1):'--';
  document.getElementById('humidity').textContent=Number.isFinite(data.humidity)?data.humidity.toFixed(0):'--';
  // derive a condition from day 1 forecast when present
  const first = (data.forecast||[])[0];
  const cond = first?.description || '';
  document.getElementById('cond-text').textContent = cond || '—';
  const use = document.getElementById('cond-icon');
  if(use) use.setAttribute('href', iconFromDescription(cond));
  document.getElementById('current').hidden=false;
}

function showForecast(list){
  const root=document.getElementById('forecast');
  const grid=document.getElementById('forecast-list');
  grid.innerHTML='';
  (list||[]).slice(0,5).forEach(d=>{
    const div=document.createElement('div');
    div.className='day';
    const icon = iconFromDescription(d.description||'');
    div.innerHTML=`
      <div class="mini"><svg><use href="${icon}"></use></svg></div>
      <div class="date">${d.date}</div>
      <div class="desc">${d.description||''}</div>
      <div class="temp">Min: ${Number.isFinite(d.minTempC)?d.minTempC.toFixed(1):'--'}°C</div>
      <div class="temp">Max: ${Number.isFinite(d.maxTempC)?d.maxTempC.toFixed(1):'--'}°C</div>
    `;
    grid.appendChild(div);
  });
  root.hidden=false;
}

async function handleSubmit(e){
  e.preventDefault();
  const city=document.getElementById('city').value.trim();
  if(!city){return}
  setStatus('Loading…');
  try{
    const data=await fetchWeather(city);
    showCurrent(data);
    showForecast(data.forecast);
    setStatus('');
  }catch(err){
    setStatus(err.message||'Something went wrong', 'error');
  }
}

document.getElementById('search-form').addEventListener('submit', handleSubmit);

// Prefill a sample city for convenience
document.getElementById('city').value='London';
