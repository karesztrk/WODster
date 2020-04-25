import React from 'react';

const Day = ({ training }) => (
  <div>
    <h2>{training[0]}</h2>
    <div>
      {training.slice(1).map(t => (
        <React.Fragment>
          {t}
          <br />
        </React.Fragment>
      ))}
    </div>
  </div>
);

export default Day;
