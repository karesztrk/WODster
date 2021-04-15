import config from '../../config.json';

type Config = {
  readonly base_url: string;
  readonly site_title: string;
  readonly site_description: string;
  readonly site_keywords: { keyword: string }[];
  readonly posts_per_page: number;
  readonly github_account: string;
  readonly tags_per_page: number;
  readonly email: string;
};

export default config as Config;
