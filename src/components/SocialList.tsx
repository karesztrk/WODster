import React from 'react';
import GitHub from '../assets/github-alt.svg';
import Email from '../assets/email.svg';
import config from '../lib/config';

export function SocialList({}) {
  return (
    <div>
      <a
        title="GitHub"
        href={`https://github.com/${config.github_account}`}
        target="_blank"
        rel="noopener"
      >
        <GitHub width={24} height={24} />
      </a>
      <a title="Email" href={`mailto:${config.email}`}>
        <Email width={24} height={24} />
      </a>
      <style jsx>{`
        a {
          display: inline-block;
        }
        a:not(:last-child) {
          margin-right: 2em;
        }
      `}</style>
    </div>
  );
}
