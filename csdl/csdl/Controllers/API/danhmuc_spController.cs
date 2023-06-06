using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using csdl.Models;

namespace csdl.Controllers.API
{
    public class danhmuc_spController : ApiController
    {
        private csdl1Entities db = new csdl1Entities();

        // GET: api/danhmuc_sp
        [HttpGet]
        [Route("api/danhmuc/getall")]
        public IQueryable<danhmuc_sp> GetDanhmuc_Sps()
        {
            return db.danhmuc_sp;
        }

        [HttpGet]
        [Route("api/danhmuc/Getdanhmuc_sp/{id}")]
        public IHttpActionResult Getdanhmuc_sp(int id)
        {
            danhmuc_sp danhmuc_sp = db.danhmuc_sp.Find(id);
            if (danhmuc_sp == null)
            {
                return NotFound();
            }

            return Ok(danhmuc_sp);
        }

        [HttpPut]
        [Route("api/danhmuc/Putdanhmuc_sp/{id}")]
        public IHttpActionResult Putdanhmuc_sp(int id, danhmuc_sp danhmuc_sp)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != danhmuc_sp.iddm)
            {
                return BadRequest();
            }

            db.Entry(danhmuc_sp).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!danhmuc_spExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        [HttpPost]
        [Route("api/danhmuc/Postdanhmuc_sp")]
        public IHttpActionResult Postdanhmuc_sp(danhmuc_sp danhmuc_sp)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.danhmuc_sp.Add(danhmuc_sp);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = danhmuc_sp.iddm }, danhmuc_sp);
        }

        [HttpDelete]
        [Route("api/danhmuc/Deletedanhmuc_sp/{id}")]
        public IHttpActionResult Deletedanhmuc_sp(int id)
        {
            danhmuc_sp danhmuc_sp = db.danhmuc_sp.Find(id);
            if (danhmuc_sp == null)
            {
                return NotFound();
            }

            db.danhmuc_sp.Remove(danhmuc_sp);
            db.SaveChanges();

            return Ok(danhmuc_sp);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool danhmuc_spExists(int id)
        {
            return db.danhmuc_sp.Count(e => e.iddm == id) > 0;
        }
    }
}