import { GetStaticPaths, GetStaticProps } from 'next';
import Layout from '../../../components/Layout';
import BasicMeta from '../../../components/meta/BasicMeta';
import OpenGraphMeta from '../../../components/meta/OpenGraphMeta';
import TagPostList from '../../../components/TagPostList';
import config from '../../../lib/config';
import {
  countPosts,
  fetchPostContent,
  listPostContent,
  PostContent,
} from '../../../lib/posts';
import { listTags, TagContent } from '../../../lib/tags';

type Props = {
  posts: PostContent[];
  tag: TagContent;
  page?: string;
  pagination: {
    current: number;
    pages: number;
  };
};
export default function Index({ posts, tag, pagination, page }: Props) {
  const url = tag && `/posts/tags/${tag.name}` + (page ? `/${page}` : '');
  return (
    <Layout>
      {tag && (
        <>
          <BasicMeta url={url} title={tag.name} />
          <OpenGraphMeta url={url} title={tag.name} />
          <TagPostList posts={posts} tag={tag} pagination={pagination} />
        </>
      )}
    </Layout>
  );
}

export const getStaticProps: GetStaticProps = async ({ params }) => {
  const queries = params.slug as string[];
  const [slug, page] = [queries[0], queries[1]];
  const posts = listPostContent(
    page ? parseInt(page as string) : 1,
    config.posts_per_page,
    slug,
  );
  const tag = {
    slug,
    name: slug,
  };
  const pagination = {
    current: page ? parseInt(page as string) : 1,
    pages: Math.ceil(countPosts(slug) / config.posts_per_page),
  };
  const props: {
    posts: PostContent[];
    tag: TagContent;
    pagination: { current: number; pages: number };
    page?: string;
  } = { posts, tag, pagination };
  if (page) {
    props.page = page;
  }
  return {
    props,
  };
};

export const getStaticPaths: GetStaticPaths = async () => {
  const tags = listTags();
  const pathsArray = tags.map((tag) => {
    const pages = Math.ceil(countPosts(tag.slug) / config.posts_per_page);
    return Array.from(Array(pages).keys()).map((page) =>
      page === 0
        ? {
            params: { slug: [tag.slug] },
          }
        : {
            params: { slug: [tag.slug, (page + 1).toString()] },
          },
    );
  });
  // flatMap() polyfill
  const paths = [].concat(...pathsArray);
  return {
    paths: paths,
    fallback: false,
  };
};
