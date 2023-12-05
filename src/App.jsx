import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ForwardPage from './Pages/forwardpage';
import ForwardComponent from './Components/forwardcomponent';

function App() {
  return (
    <Router>
      <div className="App">
        {}
        <Switch>
          {}


          {}
          <Route path="/forward-auctions" component={ForwardPage} />

          {}
          <Route path="/auction/:id" component={ForwardComponent} />

          {}
        </Switch>
      </div>
    </Router>
  );
}

export default App;
